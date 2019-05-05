package searchfilters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.tree.DefaultMutableTreeNode;

import JTreeManager.TaggedTreeNode;

public class TreeFilterDate extends treeFilter {
	
	private Date startDate, endDate;
	
	public TreeFilterDate(Date startdate, Date endDate) {
		this.startDate = startdate;
		this.endDate = endDate;
	}

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

}
