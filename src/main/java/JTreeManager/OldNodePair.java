package JTreeManager;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author gaetan
 */
public class OldNodePair {
	
	private DefaultMutableTreeNode parent, child;
	private int index;

    /**
     *
     * @param parent
     * @param child
     * @param index
     */
    public OldNodePair(DefaultMutableTreeNode parent, DefaultMutableTreeNode child, int index) {
		super();
		this.parent = parent;
		this.child = child;
		this.index = index;
	}

    /**
     *
     * @return
     */
    public DefaultMutableTreeNode getParent() {
		return parent;
	}

    /**
     *
     * @param parent
     */
    public void setParent(DefaultMutableTreeNode parent) {
		this.parent = parent;
	}

    /**
     *
     * @return
     */
    public DefaultMutableTreeNode getChild() {
		return child;
	}

    /**
     *
     * @param child
     */
    public void setChild(DefaultMutableTreeNode child) {
		this.child = child;
	}

    /**
     *
     * @return
     */
    public int getIndex() {
		return index;
	}

    /**
     *
     * @param index
     */
    public void setIndex(int index) {
		this.index = index;
	}
	
	

}
