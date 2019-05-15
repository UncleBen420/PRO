package searchfilters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import JTreeManager.TaggedTreeNode;
import meteoAPI.MeteoAPI;
import meteoAPI.MeteoPerDay;
import meteoAPI.TYPEMETEO;

/**
 *
 * @author gaetan
 */
public class MeteoTreeFilter extends AbstractTreeFilter {

	private TYPEMETEO meteo;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private List<MeteoPerDay> dates;
	private Boolean rain;
	private MeteoAPI ma = new MeteoAPI();

    /**
     *
     * @param meteo
     * @param rain
     */
    public MeteoTreeFilter(TYPEMETEO meteo, Boolean rain) {
		this.meteo = meteo;
		this.rain = rain;
		dates = ma.getListFiltreSummary(meteo);
	}

    /**
     *
     * @param node
     * @return
     */
    @Override
	public boolean analyseNode(DefaultMutableTreeNode node) {

		

		if (!(node instanceof TaggedTreeNode)) {
			return false;
		}

		TaggedTreeNode taggedTreeNode = (TaggedTreeNode) node;

		if (taggedTreeNode.getTag() != null && taggedTreeNode.getTag().equals("Date")) {

			Date nodeDate = null;
			try {
				nodeDate = df.parse(taggedTreeNode.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			boolean noDateFound = true;

			for (MeteoPerDay date : dates) {
				if (nodeDate.compareTo(date.getDate()) == 0) {

					TaggedTreeNode hours = (TaggedTreeNode) taggedTreeNode.getChildAt(0).getChildAt(0);

					for (int i = 0; i < hours.getChildCount(); i++) {
						

						if (((TaggedTreeNode) hours.getChildAt(i)).getTag().equals("Hour")
								&& !(date.getMeteo().get(Integer.parseInt(hours.getChildAt(i).toString()))
										.equals(meteo.toString()) && rain == date.getRainInfo().get(i))) {

							removeFromTree((TaggedTreeNode) hours.getChildAt(i));
							i--;

						}else {
							noDateFound = false;
						}
					}

				}

			}

			if (noDateFound) {
				removeFromTree(taggedTreeNode);
				return true;
			}else {
				return false;
			}

			

		}

		return false;
	}

    /**
     *
     * @param node
     */
    protected void filtreNode(DefaultMutableTreeNode node) {

		for (int i = 0; i < node.getChildCount(); i++) {

			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);

			if (analyseNode(child)) {
				i--;
			}else {
				if(!((TaggedTreeNode)child).getTag().equals("AfterDate")) {
					filtreNode((DefaultMutableTreeNode) node.getChildAt(i));
				}
			}
		}
	}
    
    public String toString() {
		return "Filtre de Météo";

	}

}
