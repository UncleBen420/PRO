package DFSDirectoryTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import DirectoryTree.DirectoryTree;
import Image.Image;
import SearchFilters.SearchFilter;
import interfaces.Filtrable;
import interfaces.Visitor;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
				
				if(!(f instanceof Image)) {
					System.out.println(f);
					actual[0] += f + " ";
				}
				
				
			}
			
			
		});
		
		/*test2.Filtre(new SearchFilter () {

			public void visit(Filtrable f) {
				
				
			}
			
		});*/
		
		assertArrayEquals(espected, actual);
	}
	
	
	

}
