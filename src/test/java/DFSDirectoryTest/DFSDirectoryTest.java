package DFSDirectoryTest;

import static org.junit.jupiter.api.Assertions.*;

import DirectoryTree.DirectoryTree;
import Image.Image;
import SearchFilters.SearchFilter;
import interfaces.Filtrable;
import interfaces.Visitor;

import org.junit.jupiter.api.Test;

class DFSDirectoryTest {

	@Test
	void DfsShouldBeRight() {

		DirectoryTree test = new DirectoryTree();
		
		DirectoryTree test2 = test.addChild("s1");
		
		
		test.addImage(new Image("image1"));
		
		test2.addImage(new Image("images1"));
		
		test.explore(new Visitor(){
			
			public void visit(Filtrable f) {
				System.out.println(f);
			}
			
			
		});
		
		test2.Filtre(new SearchFilter () {

			public void visit(Filtrable f) {
				
				
			}
			
		});
		
		test.explore(new Visitor(){
			
			public void visit(Filtrable f) {
				System.out.println(f);
			}
			
			
		});
		
		fail("Not yet implemented");
	}
	
	
	

}
