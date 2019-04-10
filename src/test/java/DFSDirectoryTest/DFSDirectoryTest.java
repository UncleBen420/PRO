package DFSDirectoryTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import DirectoryTree.DirectoryTree;
import Image.NodeImage;
import interfaces.Filtrable;
import interfaces.Visitor;
import searchfilters.SearchFilter;

import org.junit.jupiter.api.Test;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;


class DFSDirectoryTest {

	@Test
	void DfsShouldBeRight() {

		DirectoryTree test = new DirectoryTree();
		
		File temp = new File("C:\\Users\\remyv\\Documents\\Heig-VD\\PRO\\PRO\\src\\test\\java\\DFSDirectoryTest");
		
		test.setDirectoryTree(temp);
		
		final String[] actual = {""};
		final String[] espected = {"root test sousdossier ssdosisser suosdossier2 "};
		

		
		
		test.explore(new Visitor(){

			public void visit(Filtrable f) {
				
				if(!(f instanceof NodeImage)) {
					System.out.println(f);
					actual[0] += f + " ";
				}
						
				
			}
	
		});
		
		
		
		assertArrayEquals(espected, actual);
	}
	
	
	

}
