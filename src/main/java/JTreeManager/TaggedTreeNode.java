package JTreeManager;

import javax.swing.tree.DefaultMutableTreeNode;

public class TaggedTreeNode extends DefaultMutableTreeNode {
	
	private String tag;
	
	public TaggedTreeNode(String path, String tag) {
		super(path);
		this.tag = tag;
	}

	public TaggedTreeNode(String path) {
		super(path);
		tag = "";
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
