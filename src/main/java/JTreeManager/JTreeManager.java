package JTreeManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.apache.commons.io.FilenameUtils;

import GUI.SliderDemo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import searchfilters.treeFilter;
import jsontreeparse.JsonTreeParser;
import javax.swing.tree.DefaultMutableTreeNode;



public class JTreeManager extends JPanel {

	private List<treeFilter> Filtre = new ArrayList<treeFilter>();
	private DefaultMutableTreeNode root;
	private JTree tree;
	private File rootDirectory, JsonTree;
	private SliderDemo slider;

	private String value = "";

	public JTreeManager() {
		
		this.rootDirectory = new File(parseProperties().getProperty("imageBankPath"));
		this.JsonTree = new File(parseProperties().getProperty("JsonBankPath"));
		
		
		JsonTreeParser parser = new JsonTreeParser();
		

		if (this.JsonTree.exists()) {
			System.out.println("le chemin du passer");
			root = parser.setDirectoryTree(parseProperties().getProperty("JsonBankPath"));

		} else {
			System.out.println("le chemin du nouveau");
			parser.createXML(rootDirectory);
			root = parser.setDirectoryTree("src/jsonFile.json");
		}

		tree = null;

		tree = new JTree(root);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				TreePath treepath = e.getPath();

				value = rootDirectory.getAbsolutePath();
				
				if (FilenameUtils.getExtension(treepath.getLastPathComponent().toString()).equals("jpg")) {
					Object elements[] = treepath.getPath();

					for (int i = 1, n = elements.length; i < n; i++) {
						value += "/" + elements[i];

					}

					slider.addImage(value);

				}
			}
		});

		this.add(tree);

	}


	public void addFiltre(treeFilter f) {
		Filtre.add(f);
		f.setTree(tree);
		f.filtreTree();
	
	}

	public void removeFiltre(treeFilter f) {
		Filtre.remove(f);
		f.unfiltreTree();
	}

	private Properties parseProperties() {
		Properties properties = new Properties();
		FileReader fr = null;
		try {
			fr = new FileReader("conf.properties");
			properties.load(fr);
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return properties;
	}

	public String getValue() {
		return value;
	}

	public void setSlider(SliderDemo s) {
		slider = s;
	}
}












/*@SuppressWarnings("serial")
public class JTreeManager extends JPanel {

	private List<SearchFilter> Filtre = new ArrayList<SearchFilter>();

	private DirectoryTree root;
	private JTree tree;
	private File rootDirectory, JsonTree;
	private SliderDemo slider;

	private String value = "";

	public JTreeManager() {
		this.rootDirectory = new File(parseProperties().getProperty("imageBankPath"));
		root = new DirectoryTree();

		this.JsonTree = new File(parseProperties().getProperty("JsonBankPath"));
		if (this.JsonTree.exists()) {
			root.setDirectoryTree(this.JsonTree);

		} else {
			root.createXML(rootDirectory);
		}

		tree = null;
		updateJtree();
	}

	public void setRoot(File rootDirectory) {
		root.setDirectoryTree(rootDirectory);
		tree = null;
		this.rootDirectory = rootDirectory;
	}

	public JTreeManager(File rootDirectory) {
		root = new DirectoryTree();
		root.setDirectoryTree(rootDirectory);

		tree = null;
		this.rootDirectory = rootDirectory;

	}

	public void addFiltre(SearchFilter f) {
		Filtre.add(f);
		f.setFiltred(root);
		root.explore(f);
		updateJtree();
	}

	public void removeFiltre(SearchFilter f) {
		Filtre.remove(f);
		f.cleanFilter();
		updateJtree();
	}

	public void updateJtree() {
		if (tree != null)
			this.remove(tree);
		tree = new JTree(root);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				TreePath treepath = e.getPath();

				value = rootDirectory.getAbsolutePath();
				if (treepath.getLastPathComponent() instanceof NodeImage) {
					Object elements[] = treepath.getPath();

					for (int i = 1, n = elements.length; i < n; i++) {
						value += "/" + elements[i];

					}

					slider.addImage(value);
				}
			}
		});

		this.add(tree);

	}

	public Properties parseProperties() {
		Properties properties = new Properties();
		FileReader fr = null;
		try {
			fr = new FileReader("/mnt/Data/HEIG-VD/PRO/Code/PRO/conf.properties");
			properties.load(fr);
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return properties;
	}

	public String getValue() {
		return value;
	}

	public void setSlider(SliderDemo s) {
		slider = s;
	}
} */