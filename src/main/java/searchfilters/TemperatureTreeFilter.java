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
/**
 *
 * @author gaetan
 */
public class TemperatureTreeFilter extends AbstractTreeFilter {

	private double tempMin, tempMax;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private List<MeteoPerDay> dates;
	private MeteoAPI ma = new MeteoAPI();

    /**
     *
     * @param meteo
     * @param rain
     */
    public TemperatureTreeFilter(double tempMin, double tempMax) {
		this.tempMin = tempMin;
		this.tempMax = tempMax;	
	}

    /**
     *
     * @param node
     * @return
     */
    @Override
	public boolean analyseNode(DefaultMutableTreeNode node) {

		dates = ma.getListFiltreTemperature(tempMin, tempMax);

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
								&& (date.getTemperature().get(Integer.parseInt(hours.getChildAt(i).toString()))
										.equals(99.))) {

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

}
