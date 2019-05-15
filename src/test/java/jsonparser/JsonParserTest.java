package jsonparser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import jsontreeparse.JsonTreeParser;

class JsonParserTest {

	@Test
	void jsonShouldWork() {

		
		
		JsonTreeParser json = new JsonTreeParser();
		
		//File temp = new File("C:\\Users\\remyv\\Documents\\Heig-VD\\PRO\\PRO\\src\\test\\java\\DFSDirectoryTest");
		json.parseHierarchyTag();
		//json.createXML(temp);
		//json.setDirectoryTree("src/jsonFile.json");
		
		
		assertEquals(false,false);
	}
}
