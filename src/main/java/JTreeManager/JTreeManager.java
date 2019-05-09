package JTreeManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTree;
import org.apache.commons.io.FilenameUtils;
import GUI.SliderDemo;
import GUI.ViewerTable;
import Tag.CsvParser;
import Tag.Parser;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import searchfilters.AbstractTreeFilter;
import jsontreeparse.JsonTreeParser;
import properties.PropertiesHandler;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author gaetan
 */
public class JTreeManager extends JPanel {

	private List<AbstractTreeFilter> Filtre = new ArrayList<AbstractTreeFilter>();
	private DefaultMutableTreeNode root;
	private JTree tree;
	private File rootDirectory, JsonTree;
	private SliderDemo slider;
	private ViewerTable table;
	private Parser parserTag = new Parser();
	private final Semaphore mutex = new Semaphore(1);

	private String value = "";

    /**
     *
     */
    public JTreeManager() {

		Properties properties = PropertiesHandler.parseProperties();

		this.rootDirectory = new File(properties.getProperty("imageBankPath"));
		this.JsonTree = new File(properties.getProperty("JsonBankPath"));

		JsonTreeParser parser = new JsonTreeParser();

		if (this.JsonTree.exists()) {
			System.out.println("le chemin du passer");
			root = parser.setDirectoryTree(properties.getProperty("JsonBankPath"));

		} else {
			System.out.println("le chemin du nouveau");
			parser.createXML(rootDirectory);

			root = parser.setDirectoryTree((new File(properties.getProperty("JsonBankPath")).getAbsolutePath()));
		}

		tree = null;

		tree = new JTree(root);

		tree.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					if (FilenameUtils.getExtension(((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).toString()).equals("jpg")) {

						value = rootDirectory.getAbsolutePath();

						TreeNode[] elements = ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getPath();
						for (int i = 1, n = elements.length; i < n; i++) {
							value += "/" + elements[i];

						}
						
						slider.addImage(value);

						try {
							table.setTags(CsvParser.getTag(parserTag.getTag(value)));
						} catch (IOException ex) {
							Logger.getLogger(JTreeManager.class.getName()).log(Level.SEVERE, null, ex);
						}

					}
				}
			}

		});

		this.add(tree);

	}

    /**
     *
     * @param f
     */
    public void addFiltre(final AbstractTreeFilter f) {

		Thread thread = new Thread() {
			public void run() {

				try {
					mutex.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Filtre.add(f);
				f.setTree(tree);
				f.filtreTree();

				mutex.release();

			}
		};

		thread.start();

	}

    /**
     *
     * @param f
     */
    public void removeFiltre(final AbstractTreeFilter f) {

		Thread thread = new Thread() {
			public void run() {

				try {
					mutex.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				List<AbstractTreeFilter> temp = new ArrayList<AbstractTreeFilter>();

				int i = Filtre.size();

				for (i--; i >= Filtre.indexOf(f); i--) {

					Filtre.get(i).unfiltreTree();

				}

				Filtre.remove(f);

				for (i++; i < Filtre.size(); i++) {

					Filtre.get(i).filtreTree();

				}
				
				mutex.release();

			}
		};

		thread.start();

	}

    /**
     *
     * @return
     */
    public String getValue() {
		return value;
	}

    /**
     *
     * @param s
     */
    public void setSlider(SliderDemo s) {
		slider = s;
	}

    /**
     *
     * @param t
     */
    public void setTable(ViewerTable t) {
		table = t;
	}
}
