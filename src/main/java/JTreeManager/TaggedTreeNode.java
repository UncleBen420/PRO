package JTreeManager;

import javax.swing.tree.DefaultMutableTreeNode;

public class TaggedTreeNode extends DefaultMutableTreeNode {
	
	private String tag;
	
	public TaggedTreeNode(String path, String tag) {
		super(path);
		System.out.println(tag);
		tag = "";
	}

	public TaggedTreeNode(String path) {
		super(path);
		tag = "";
	}

}
