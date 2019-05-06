package JTreeManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import GUI.ViewerTable;
import Tag.CsvParser;
import Tag.Parser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import searchfilters.treeFilter;
import jsontreeparse.JsonTreeParser;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;



public class JTreeManager extends JPanel {

	private List<treeFilter> Filtre = new ArrayList<treeFilter>();
	private DefaultMutableTreeNode root;
	private JTree tree;
	private File rootDirectory, JsonTree;
	private SliderDemo slider;
        private ViewerTable table;
        private Parser parserTag = new Parser();

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
			root = parser.setDirectoryTree("/mnt/Data/HEIG-VD/PRO/Code/PRO/src/jsonFile.json");
		}

		tree = null;

		tree = new JTree(root);


		
		tree.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            if (e.getClickCount() == 2) {
	            	
	            	          
	                
	                
	                if (FilenameUtils.getExtension(((DefaultMutableTreeNode)tree.getLastSelectedPathComponent()).toString()).equals("jpg")) {
	                	
	                	value = rootDirectory.getAbsolutePath();   
	                	
	                	TreeNode[] elements = ((DefaultMutableTreeNode)tree.getLastSelectedPathComponent()).getPath();
	                	System.out.println(((DefaultMutableTreeNode)tree.getLastSelectedPathComponent()).toString());
	                	
                                for (int i = 1, n = elements.length; i < n; i++) {
                                        value += "/" + elements[i];

                                }

                                System.out.println(value);

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
        
        public void setTable(ViewerTable t) {
		table = t;
	}
}
