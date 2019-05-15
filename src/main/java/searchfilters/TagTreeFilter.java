package searchfilters;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import JTreeManager.TaggedTreeNode;
import Tag.Parser;
import meteoAPI.MeteoAPI;
import meteoAPI.MeteoPerDay;
import properties.PropertiesHandler;
/**
 *
 * @author gaetan
 */
public class TagTreeFilter extends AbstractTreeFilter {

	private Parser parser = new Parser();
	private File rootDirectory;
	private boolean tagged;
	
	public TagTreeFilter(boolean tagged) {

		this.tagged = tagged;
		this.rootDirectory = new File(PropertiesHandler.parseProperties().getProperty("imageBankPath"));
	}
	

    /**
     *
     * @param node
     * @return
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

			try {
				
				if(parser.isTagged(new File(value)) == tagged) {
					return false;
				}else {
					return true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		}
		
		return false;
	}
    
    public String toString() {
		return "Tag Filter";

	}

}
