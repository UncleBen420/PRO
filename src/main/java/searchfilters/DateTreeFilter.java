package searchfilters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.tree.DefaultMutableTreeNode;

import JTreeManager.TaggedTreeNode;

/**
 * extention de abstractTreeFilter permettant de filtrer les noeud par rapport au date
 * @author Groupe PRO B-9
 */
public class DateTreeFilter extends AbstractTreeFilter {
	
	private Date startDate, endDate;
	
    /**
     * Constructeur du filtre
     * @param startdate date de début du filtrage
     * @param endDate date de fin du filtrage
     */
    public DateTreeFilter(Date startdate, Date endDate) {
		this.startDate = startdate;
		this.endDate = endDate;
	}

    /**
     * regarde si un noeud est un noeud de date et s'y il est compris dans les date min et max
     * @param node le noeud étant analysé
     * @return si oui ou non on doit l'enlevé de l'arbre
     */
    @Override
	public boolean analyseNode(DefaultMutableTreeNode node) {
		
		if(!(node instanceof TaggedTreeNode)) {
			return false;
		}
		
		TaggedTreeNode taggedTreeNode = (TaggedTreeNode)node;
		
		if(taggedTreeNode.getTag() != null && taggedTreeNode.getTag().equals("Date")) {
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date nodeDate = null;
			try {
				nodeDate = df.parse(taggedTreeNode.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(startDate.compareTo(nodeDate) <= 0 && endDate.compareTo(nodeDate) >= 0) {
				return false;
			}else {
				return true;
			}
		}
		
		return false;
	}
    
    /**
     * modification de filtreNode pour qu'il ne parcours pas les noeud plus loin que date
     */
    @Override
    protected void filtreNode(DefaultMutableTreeNode node) {

		for (int i = 0; i < node.getChildCount(); i++) {

			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);

			if (analyseNode(child)) {
				removeFromTree(child);
				i--;
			} else {
				if(!((TaggedTreeNode)child).getTag().equals("AfterDate")) {
					filtreNode((DefaultMutableTreeNode) node.getChildAt(i));
				}
			}
		}
	}
    
    public String toString() {
		return "Date Filter";

	}

}
