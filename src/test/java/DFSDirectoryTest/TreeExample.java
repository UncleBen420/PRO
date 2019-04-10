package DFSDirectoryTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import DirectoryTree.DirectoryTree;
import JTreeManager.JTreeManager;
import interfaces.Filtrable;
import searchfilters.SearchFilter;
public class TreeExample extends JFrame
{
    private JTree tree;
    private String value = "";
    public TreeExample()
    {
    	
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
    	DirectoryTree test = new DirectoryTree();
    	
    	JTreeManager manager = new JTreeManager(new File(properties.getProperty("imageBankPath")));
    	
    	this.add(new JScrollPane(manager));
    	
    	manager.updateJtree();
        
        SearchFilter filtre = new SearchFilter(test){

			public void visit(Filtrable f) {
				
				if(f.toString().equals("src")) {
					f.Filtre(this);
				}
				
			}	
		};
        
		manager.addFiltre(filtre);
		manager.removeFiltre(filtre);
		
		
		
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JTree Example");       
        this.pack();
        this.setVisible(true);
        
        
        
        
        
        
        
    }
     
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TreeExample();
            }
        });
    }       
}