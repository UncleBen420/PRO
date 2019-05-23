package jsonparser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.jupiter.api.Test;

import imageparser.ImageParser;
import jsontreeparse.JsonTreeParser;
import properties.PropertiesHandler;

public class JsonTreeParserTest {

	@Test
	void getPixelBufferShouldReturnTheRightArray() {

		Properties properties = PropertiesHandler.parseProperties();
		JsonTreeParser js = new JsonTreeParser();
		js.createJson(new File("src/test/java/jsonparser/testImages/"), 1);
		DefaultMutableTreeNode temp = js
				.setDirectoryTree((new File(properties.getProperty("JsonBankPath")).getAbsolutePath()));

		String actual = printTree(temp);
		System.out.println(actual);
		String expected = "Caméra 012017-02-23001jpg09561553444168110s (8th copy).jpg";

		assertEquals(expected, actual);

	}

	private String printTree(DefaultMutableTreeNode node) {

		String temp = "";

		for (int i = 0; i < node.getChildCount(); i++) {

			temp += (node.getChildAt(i));
			temp += printTree((DefaultMutableTreeNode) node.getChildAt(i));

		}

		return temp;

	}

}
