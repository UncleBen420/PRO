
package searchfilters;

import java.io.File;
import java.text.ParseException;
import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import JTreeManager.TaggedTreeNode;
import imageparser.ImageParser;
import meteoAPI.MeteoPerDay;
import properties.PropertiesHandler;

/**
 * extension de abstractTreeFilter permettant de filtrer les noeud par rapport au ratio
 * @author Groupe PRO B-9
 */
public class RatioTreeFilter extends AbstractTreeFilter {

	private ImageParser ip = new ImageParser();
	private int min, max, tolerance, precision;
	private File rootDirectory;

	public RatioTreeFilter(int min, int max, int tolerance, int precision) {
		this.min = min;
		this.max = max;
		this.tolerance = tolerance;
		this.precision = precision;
		this.rootDirectory = new File(PropertiesHandler.parseProperties().getProperty("imageBankPath"));
	}

	/**
     * analyse les noeuds, sur les caract�ristes entre les diff�rences des images d'une s�quance
     * @param node le noeud �tant analys�
     * @return si oui ou non on doit l'enlev� de l'arbre
     */
    @Override
	public boolean analyseNode(DefaultMutableTreeNode node) {

		if (!(node instanceof TaggedTreeNode)) {
			return false;
		}

		TaggedTreeNode taggedTreeNode = (TaggedTreeNode) node;

		if (taggedTreeNode.getTag() != null && taggedTreeNode.getTag().equals("Minute")) {

			int mean = 0;

			for (int i = 0; i < taggedTreeNode.getChildCount() - 1; i++) {

				TaggedTreeNode taggedChild = (TaggedTreeNode) taggedTreeNode.getChildAt(i);
				TaggedTreeNode taggedBrother = (TaggedTreeNode) taggedTreeNode.getChildAt(i + 1);

				if (taggedChild.getTag().equals("Image") && taggedBrother.getTag().equals("Image")) {

					String path1 = rootDirectory.getAbsolutePath();
					String path2 = rootDirectory.getAbsolutePath();

					TreeNode[] elements1 = taggedChild.getPath();
					TreeNode[] elements2 = taggedBrother.getPath();
					for (int j = 1, n = elements1.length; j < n; j++) {
						path1 += "/" + elements1[j];
						path2 += "/" + elements2[j];

					}

					try {
						int test = ip.compareImageRatioOpti(path1, path2, 100, 0, tolerance, precision);
						mean += test;
					} catch (Exception e) {
						return true;
					}

				}

			}

			if(taggedTreeNode.getChildCount() - 1 != 0)
			mean /= taggedTreeNode.getChildCount() - 1;

			if (min > mean || mean > max) {
				
				return true;
			}
		}

		return false;
	}

	protected void filtreNode(DefaultMutableTreeNode node) {

		for (int i = 0; i < node.getChildCount(); i++) {

			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);

			if (analyseNode(child)) {
				i--;
				removeFromTree(child);
			} else {
				if (!((TaggedTreeNode) child).getTag().equals("Image")) {
					filtreNode((DefaultMutableTreeNode) node.getChildAt(i));

				}
			}
		}

	}
	
	public String toString() {
		return "Image's differances Filter";

	}
}
