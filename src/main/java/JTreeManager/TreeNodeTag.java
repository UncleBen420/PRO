package JTreeManager;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeNodeTag extends DefaultMutableTreeNode {
	
	private String tag;
	
	public TreeNodeTag(String path, String tag) {
		super(path);
		tag = "";
	}

	public TreeNodeTag(String path) {
		super(path);
		tag = "";
	}

}
