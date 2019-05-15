package searchfilters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.tree.DefaultMutableTreeNode;

import JTreeManager.TaggedTreeNode;

/**
 *
 * @author gaetan
 */
public class DateTreeFilter extends AbstractTreeFilter {
	
	private Date startDate, endDate;
	
    /**
     *
     * @param startdate
     * @param endDate
     */
    public DateTreeFilter(Date startdate, Date endDate) {
		this.startDate = startdate;
		this.endDate = endDate;
	}

    /**
     *
     * @param node
     * @return
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
				System.out.println("trouver");
				return false;
			}else {
				return true;
			}
		}
		
		return false;
	}
    
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
