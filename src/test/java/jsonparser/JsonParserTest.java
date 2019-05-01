package jsonparser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;
import jsontreeparse.JsonTreeParser;

class JsonParserTest {

	@Test
	void jsonShouldWork() {

		
		
		JsonTreeParser json = new JsonTreeParser();
		
		File temp = new File("C:\\Users\\remyv\\Documents\\Heig-VD\\PRO\\PRO\\src\\test\\java\\DFSDirectoryTest");
		
		json.createXML(temp);
		json.setDirectoryTree("src/jsonFile.json");
		
		
		assertEquals(false,true);
	}
}
