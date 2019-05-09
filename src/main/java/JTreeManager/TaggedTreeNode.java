package JTreeManager;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author gaetan
 */
public class TaggedTreeNode extends DefaultMutableTreeNode {
	
	private String tag;
	
    /**
     *
     * @param path
     * @param tag
     */
    public TaggedTreeNode(String path, String tag) {
		super(path);
		this.tag = tag;
	}

    /**
     *
     * @param path
     */
    public TaggedTreeNode(String path) {
		super(path);
		tag = "";
	}

    /**
     *
     * @return
     */
    public String getTag() {
		return tag;
	}

    /**
     *
     * @param tag
     */
    public void setTag(String tag) {
		this.tag = tag;
	}

}
