package JTreeManager;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Extention de DefaultMutableTreeNode permettant d'ajouter un tag au noeud
 * @author Groupe PRO B-9
 */
public class TaggedTreeNode extends DefaultMutableTreeNode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8501672794313810915L;
	private String tag;
	
    /**
     * constructeur du noeud
     * @param path le chemin du noeud
     * @param tag le tag du noeud
     */
    public TaggedTreeNode(String path, String tag) {
		super(path);
		this.tag = tag;
	}

    /**
     * constructeur du noeud
     * @param path le chemin du noeud
     */
    public TaggedTreeNode(String path) {
		super(path);
		tag = "";
	}

    /**
     *
     * @return retourne le tag
     */
    public String getTag() {
		return tag;
	}


    public void setTag(String tag) {
		this.tag = tag;
	}

}
