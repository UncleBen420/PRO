package searchfilters;

import java.util.Stack;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import JTreeManager.OldNodePair;

/**
 *
 * @author gaetan
 */
public abstract class AbstractTreeFilter {
	
	static int counter = 0;
	{
		counter++;
	}

	private int num = counter;
	private Stack<OldNodePair> filtredElements = new Stack<OldNodePair>();
	private JTree tree;
	private DefaultTreeModel model;
	private DefaultMutableTreeNode root;

    /**
     *
     * @param tree
     */
    public AbstractTreeFilter(JTree tree) {
		this.tree = tree;
		
	}

    /**
     *
     */
    public AbstractTreeFilter() {
		this.tree = null;
	}

    /**
     *
     * @return
     */
    public JTree getTree() {
		return tree;
	}

    /**
     *
     * @param tree
     */
    public void setTree(JTree tree) {
		this.tree = tree;
		this.model = (DefaultTreeModel) tree.getModel();
		root = (DefaultMutableTreeNode) model.getRoot();
	}

    /**
     *
     * @param node
     */
    protected void removeFromTree(DefaultMutableTreeNode node) {

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

		filtredElements.push(new OldNodePair(parent, node, parent.getIndex(node)));

		model.removeNodeFromParent(node);
		
		

	}

    /**
     *
     */
    public void PopToTree() {
    	
		OldNodePair lastFiltredNode = filtredElements.pop();

		model.insertNodeInto(lastFiltredNode.getChild(), lastFiltredNode.getParent(), lastFiltredNode.getIndex());
		

	}

    /**
     *
     */
    public void filtreTree() {
    	
		if (analyseNode(root)) {
			removeFromTree(root);
		} else {
			filtreNode(root);
		}
		model.reload();
		

	}

    /**
     *
     * @param node
     */
    protected void filtreNode(DefaultMutableTreeNode node) {

		for (int i = 0; i < node.getChildCount(); i++) {

			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);

			if (analyseNode(child)) {
				removeFromTree(child);
				i--;
			} else {
				filtreNode((DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}

    /**
     *
     */
    public void unfiltreTree() {
		
		while (!filtredElements.isEmpty()) {
			PopToTree();
		}
		model.reload();
	}

    /**
     *
     * @param node
     * @return
     */
    abstract public boolean analyseNode(DefaultMutableTreeNode node);

	public String testToString() {
		String temp = num + "filter:\n";

		for (OldNodePair o : filtredElements) {
			temp += o.getChild() + " " + o.getParent() + " " + o.getIndex() + "\n";
		}

		return temp;

	}
	
	public String toString() {
		return "Abstract filter";

	}

}
