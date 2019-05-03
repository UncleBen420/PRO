package JTreeManager;

import javax.swing.tree.DefaultMutableTreeNode;

public class OldNodePair {
	
	private DefaultMutableTreeNode parent, child;
	private int index;

	public OldNodePair(DefaultMutableTreeNode parent, DefaultMutableTreeNode child, int index) {
		super();
		this.parent = parent;
		this.child = child;
		this.index = index;
	}

	public DefaultMutableTreeNode getParent() {
		return parent;
	}

	public void setParent(DefaultMutableTreeNode parent) {
		this.parent = parent;
	}

	public DefaultMutableTreeNode getChild() {
		return child;
	}

	public void setChild(DefaultMutableTreeNode child) {
		this.child = child;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	

}
