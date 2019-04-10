package SearchFilterTest;

import interfaces.Filtrable;
import interfaces.Visitor;
import searchfilters.SearchFilter;
import DirectoryTree.DirectoryTree;
import Image.NodeImage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class SearchFiltreTest {

	@Test
	void UserCouldAddAFilter() {
		
		DirectoryTree test = new DirectoryTree();
		
		File temp = new File("C:\\Users\\remyv\\Documents\\Heig-VD\\PRO\\PRO\\src\\test\\java\\DFSDirectoryTest");
		final String[] actual = {""};
		final String[] espected = {"root test sousdossier ssdosisser suosdossier2 "};		
		test.setDirectoryTree(temp);
		
		SearchFilter filtre = new SearchFilter(test){

			public void visit(Filtrable f) {
				
				if(f.toString().equals("sousdossier")) {
					f.Filtre(this);
				}
				
			}	
		};
		
		SearchFilter filtre2 = new SearchFilter(test){

			public void visit(Filtrable f) {
				
				if(f.toString().equals("ssdosisser")) {
					f.Filtre(this);
				}
				
			}	
		};
		
		Visitor visitor = new Visitor(){

			public void visit(Filtrable f) {
				
				if(!(f instanceof NodeImage)) {
					System.out.println(f + "  "+ f.getFilter());
					actual[0] += f + " ";
				}		
			}
		};
		
		test.explore(visitor);
		test.explore(filtre2);
		
		System.out.println("werth");
		
		test.explore(visitor);
		test.explore(filtre);
		test.explore(visitor);

		filtre.cleanFilter();
		filtre2.cleanFilter();
		
		actual[0] = "";	
		test.explore(visitor);

		
		assertArrayEquals(espected, actual);
	}

	@Test
	void UserCouldDeleteAFilter() {
		fail("Not yet implemented");
	}
	
	@Test
	void FilterAreCorrect() {
		fail("Not yet implemented");
	}
	
}
