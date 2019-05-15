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
						System.out.println(path1);
						System.out.println(path2);
						System.out.println(test);
						mean += test;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
						return true;
					}

				}

			}

			if(taggedTreeNode.getChildCount() - 1 != 0)
			mean /= taggedTreeNode.getChildCount() - 1;

			System.out.println("mean" + mean);
			
			if (min > mean || mean > max) {
				
				return true;
			}
		}

		return false;
	}

	/**
	 *
	 * @param node
	 */
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
}