package DFSDirectoryTest;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import DirectoryTree.DirectoryTree;
import SearchFilters.SearchFilter;
import interfaces.Filtrable;
public class TreeExample extends JFrame
{
    private JTree tree;
    public TreeExample()
    {
    	
    	DirectoryTree test = new DirectoryTree();
    	
    	File temp = new File("C:\\Users\\remyv\\Documents\\Heig-VD");
		
		test.setDirectoryTree(temp);
        tree = new JTree(test);
        add(tree);
        
        SearchFilter filtre = new SearchFilter(test){

			public void visit(Filtrable f) {
				
				if(f.toString().equals("RES")) {
					System.out.println("bite");
					f.Filtre(this);
				}
				
			}	
		};
        
		test.explore(filtre);
		
		remove(tree);
		tree = new JTree(test);
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