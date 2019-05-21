package JTreeManager;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Cette classe permet d'associer des noeuds entre eux ainsi que de stocker un index.
 * @author Groupe PRO B-9
 */
public class OldNodePair {
	
	private DefaultMutableTreeNode parent, child;
	private int index;

    /**
     * Constructeur
     * @param parent le noeud parent du noeud enfant
     * @param child le noeud enfant
     * @param index l'index ou est placer le noeud enfant
     */
    public OldNodePair(DefaultMutableTreeNode parent, DefaultMutableTreeNode child, int index) {
		super();
		this.parent = parent;
		this.child = child;
		this.index = index;
	}

    /**
     * @return retourne le parent
     */
    public DefaultMutableTreeNode getParent() {
		return parent;
	}

    public void setParent(DefaultMutableTreeNode parent) {
		this.parent = parent;
	}

    /**
     * @return retourne l'enfant
     */
    public DefaultMutableTreeNode getChild() {
		return child;
	}

    public void setChild(DefaultMutableTreeNode child) {
		this.child = child;
	}

    /**
     *
     * @return retourne l'index
     */
    public int getIndex() {
		return index;
	}


    public void setIndex(int index) {
		this.index = index;
	}
	
	

}
