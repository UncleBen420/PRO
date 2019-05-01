package DFSDirectoryTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import JTreeManager.JTreeManager;
import searchfilters.treeFilter;
public class TreeExample extends JFrame
{
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
    	JTreeManager manager = new JTreeManager();
    	
    	
    	manager.addFiltre(new treeFilter() {

			@Override
			public boolean analyseNode(DefaultMutableTreeNode node) {
				if(node.toString().equals("Desktop") || node.toString().equals("Documents")) {
					System.out.println("trouver");
					return true;
					
				}
				
				return false;
			}});
    	
    	
    	this.add(new JScrollPane(manager));
		
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