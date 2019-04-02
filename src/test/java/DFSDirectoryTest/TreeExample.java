package DFSDirectoryTest;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import DirectoryTree.DirectoryTree;
public class TreeExample extends JFrame
{
    private JTree tree;
    public TreeExample()
    {
    	
    	DirectoryTree test = new DirectoryTree();
    	
    	File temp = new File("C:\\Users\\remyv\\Documents\\Heig-VD");
		
		test.setDirectoryTree(temp);
    	
    	
        tree = test.setJtree();
        add(tree);
         
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