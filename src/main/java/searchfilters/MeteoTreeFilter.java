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
 * Extention de la classe abstractTreeFilter permetant de trier les noeuds de dates en fonction de la m�t�o ce jour la. 
 * @author Groupe PRO B-9
 */
public class MeteoTreeFilter extends AbstractTreeFilter {

	private TYPEMETEO meteo;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private List<MeteoPerDay> dates;
	private MeteoAPI ma = new MeteoAPI();

    /**
     * Constructeur de filtre m�t�o
     * @param meteo la m�t�o voulue
     */
    public MeteoTreeFilter(TYPEMETEO meteo) {
		this.meteo = meteo;
		dates = ma.getListFiltreSummary(meteo);
	}

    /**
     * analyse les noeuds, si c'est des noeuds de date, la m�thode regarde si la date a la bonne m�t�o (pluie, beau,...)  
     * @param node le noeud �tant analys�
     * @return si oui ou non on doit l'enlev� de l'arbre
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
					
					date.setProper();

					
					if(!(taggedTreeNode.getChildCount() != 0 && taggedTreeNode.getChildAt(0).getChildCount() != 0)) {
						removeFromTree(taggedTreeNode);
						return true;
						
					}
					
					TaggedTreeNode hours = (TaggedTreeNode) taggedTreeNode.getChildAt(0).getChildAt(0);

					for (int i = 0; i < hours.getChildCount(); i++) {
						
						
						if (((TaggedTreeNode) hours.getChildAt(i)).getTag().equals("Hour")
								&& !(date.getMeteo().get(Integer.parseInt(hours.getChildAt(i).toString()))
										.equals(meteo.toString()))) {

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
     * modification de filtreNode pour qu'il ne parcours pas les noeud plus loin que date
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
		return "Filtre de Meteo";

	}

}
