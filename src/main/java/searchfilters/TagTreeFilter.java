package searchfilters;

import java.io.File;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import JTreeManager.TaggedTreeNode;
import Tag.Parser;
import Tag.TagHistory;
import properties.PropertiesHandler;
/**
 * extension de abstractTreeFilter permettant de filtrer les noeud par rapport aux tags
 * @author Groupe PRO B-9
 */
public class TagTreeFilter extends AbstractTreeFilter {

	private final File rootDirectory;
	private final boolean tagged;
	
	public TagTreeFilter(boolean tagged) {

		this.tagged = tagged;
		this.rootDirectory = new File(PropertiesHandler.parseProperties().getProperty("imageBankPath"));
                TagHistory.getPaths();
	}
	

    /**
     * Regarde si le noeud est une image et si cette image est taggee.
     * @param node le noeud etant analyse
     * @return si oui ou non on doit l'enleve de l'arbre
     */
    @Override
	public boolean analyseNode(DefaultMutableTreeNode node) {
    	
    	

		if(!(node instanceof TaggedTreeNode)) {
			return false;
		}
		
		TaggedTreeNode taggedTreeNode = (TaggedTreeNode)node;
		
		if(taggedTreeNode.getTag() != null && taggedTreeNode.getTag().equals("Image")) {
			
			String value = rootDirectory.getAbsolutePath();

			TreeNode[] elements = node.getPath();
			for (int i = 1, n = elements.length; i < n; i++) {
				value += "/" + elements[i];

			}
                        
                        return TagHistory.findTag(value) != tagged;
		}
		
		return false;
	}
    
    public String toString() {
		return "Tag Filter";

	}

}
