package searchfilters;

import java.util.Stack;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import JTreeManager.OldNodePair;

public abstract class treeFilter {
	
	static int counter = 0;
	{
		counter++;
	}

	private int num = counter;
	private Stack<OldNodePair> filtredElements = new Stack<OldNodePair>();
	private JTree tree;

	public treeFilter(JTree tree) {
		this.tree = tree;
	}

	public treeFilter() {
		this.tree = null;
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public void removeFromTree(DefaultMutableTreeNode node) {

		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

		filtredElements.push(new OldNodePair(parent, node, parent.getIndex(node)));

		model.removeNodeFromParent(node);
		model.reload(node);

	}

	public void PopToTree() {

		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

		OldNodePair lastFiltredNode = filtredElements.pop();

		model.insertNodeInto(lastFiltredNode.getChild(), lastFiltredNode.getParent(), lastFiltredNode.getIndex());
		model.reload(lastFiltredNode.getParent());

	}

	public void filtreTree() {
		
		System.out.println("filtre :" + this);

		final DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();

		if (analyseNode(root)) {
			removeFromTree(root);
		} else {
			filtreNode(root);
		}

	}

	private void filtreNode(DefaultMutableTreeNode node) {

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

	public void unfiltreTree() {
		
		System.out.println("unfiltre :" + this);
		
		while (!filtredElements.isEmpty()) {
			PopToTree();
		}
	}

	abstract public boolean analyseNode(DefaultMutableTreeNode node);

	public String toString() {
		String temp = num + "filter:\n";

		for (OldNodePair o : filtredElements) {
			temp += o.getChild() + " " + o.getParent() + " " + o.getIndex() + "\n";
		}

		return temp;

	}

}
