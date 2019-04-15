package JTreeManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import DirectoryTree.DirectoryTree;
import GUI.SliderDemo;
import Image.NodeImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import searchfilters.SearchFilter;

@SuppressWarnings("serial")
public class JTreeManager extends JPanel{

	private List<SearchFilter> Filtre = new ArrayList<SearchFilter>();

	private DirectoryTree root;
	private JTree tree;
        private File rootDirectory;
        private SliderDemo slider;

	private String value = "";
        
        public JTreeManager(){
            this.rootDirectory = new File(parseProperties().getProperty("imageBankPath"));
            root = new DirectoryTree();
            root.setDirectoryTree(this.rootDirectory);
            tree = null;
            updateJtree();
        }
        
        public void setRoot(File rootDirectory){
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
				if(treepath.getLastPathComponent() instanceof NodeImage) {
                                    Object elements[] = treepath.getPath();

                                    for (int i = 1, n = elements.length; i < n; i++) {
                                            value +="/" +  elements[i];

                                    }
                                    
                                    slider.addImage(value);
				}
			}
		});

		this.add(tree);

	}
        
        public Properties parseProperties(){
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
        
        public String getValue(){
            return value;
        }
        
        public void setSlider(SliderDemo s){
            slider = s;
        }
}
