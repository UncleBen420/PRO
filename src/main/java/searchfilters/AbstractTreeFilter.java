package searchfilters;

import java.util.Stack;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import JTreeManager.OldNodePair;

/**
 * Cette classe implemente les methodes de base pour filtrer sur un jtree
 * @author Groupe PRO B-9
 */
public abstract class AbstractTreeFilter {

	static int counter = 0; // utiliser pour les test
	{
		counter++;
	}

	private int num = counter;
	private Stack<OldNodePair> filtredElements = new Stack<OldNodePair>();
	private JTree tree;
	private DefaultTreeModel model;
	private DefaultMutableTreeNode root;

    /**
     * Constructeur du filtre
     * @param tree l'arbre composer de DefaultMutableTreeNode
     */
    public AbstractTreeFilter(JTree tree) {
		this.tree = tree;
		this.model = (DefaultTreeModel) tree.getModel();
		root = (DefaultMutableTreeNode) model.getRoot();
		
	}

    /**
     * Constructeur du filtre
     */
    public AbstractTreeFilter() {
		this.tree = null;
	}

    /**
     * get l'arbre sur lequelle ce filtre pointe
     * @return l'arbre sur lequelle cette fonction filtre
     */
    public JTree getTree() {
		return tree;
	}

    /**
     * permet de placer sur quel arbre va filtrer le filtre
     * @param tree
     */
    public void setTree(JTree tree) {
		this.tree = tree;
		this.model = (DefaultTreeModel) tree.getModel();
		root = (DefaultMutableTreeNode) model.getRoot();
	}

    /**
     * enleve un noeud de l'arbre et ces enfant
     * @param node le noeud a enlever
     */
    protected void removeFromTree(DefaultMutableTreeNode node) {


		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

		filtredElements.push(new OldNodePair(parent, node, parent.getIndex(node)));

		model.removeNodeFromParent(node);
		
		

	}

    /**
     * remet le dernier element enlevé dans l'arbre
     */
    public void PopToTree() {
    	
		OldNodePair lastFiltredNode = filtredElements.pop();

		model.insertNodeInto(lastFiltredNode.getChild(), lastFiltredNode.getParent(), lastFiltredNode.getIndex());
		

	}

    /**
     * Filtre tous l'arbre en appliquant une condition de recherche
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
     * Filtre l'arbre en applicant une condition depuis la racine node.
     * @param node le noeud sur lequel nous filtrons
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
     * remet dans l'arbre tous les noeud enlevé
     */
    public void unfiltreTree() {
		
		while (!filtredElements.isEmpty()) {
			PopToTree();
		}
		model.reload();
	}

    /**
     *
     * @param node le noeud analyser
     * @return si oui ou non le noeud doit etre enlever
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
