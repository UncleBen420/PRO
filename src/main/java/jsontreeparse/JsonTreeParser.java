package jsontreeparse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.commons.io.FilenameUtils;
import com.google.gson.*;
import JTreeManager.TaggedTreeNode;
import Tag.CsvParser;
import Tag.Parser;
import Tag.TagHistory;
import exceptionHandler.LogFileWritingHandler;
import properties.PropertiesHandler;

/**
 *
 * @author gaetan
 */
public class JsonTreeParser {

	private String[] hierarchyTag;
	private Parser p = new Parser();

	/**
	 *
	 */
	public void parseHierarchyTag() {

		Properties properties = PropertiesHandler.parseProperties();
		hierarchyTag = ((String) properties.get("hierarchyTag")).split("/");
	}

	/**
	 *
	 * @param rootDirectory
	 */
	public void createXML(File rootDirectory) {
		try {

			int i = 0;
			parseHierarchyTag();
			Properties properties = PropertiesHandler.parseProperties();

			BufferedWriter file = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(properties.getProperty("JsonBankPath")).getAbsolutePath()),
					StandardCharsets.UTF_16));
			JsonArray tree;
			JsonObject temp = new JsonObject();
			try {

				tree = setXML(rootDirectory, i);

				temp.add("root", tree);

			} catch (ArrayIndexOutOfBoundsException e) {
				FileWriter error = new FileWriter("Error.txt");
				error.write(e.toString());
				error.flush();
				error.close();
			}

			file.write(temp.toString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @param rootDirectory
	 * @param i
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public JsonArray setXML(File rootDirectory, int i) throws ArrayIndexOutOfBoundsException {

		if (rootDirectory.isDirectory()) {

			JsonArray dirArray = new JsonArray();

			File[] childrenDir = rootDirectory.listFiles();
			if (childrenDir != null) {
				Arrays.sort(childrenDir);

				for (File child : childrenDir) {

					if (child.isDirectory()) {

						JsonObject temp = new JsonObject();
						temp.addProperty("name", child.getName());
						temp.addProperty("tag", hierarchyTag[i]);
						temp.add("nextDir", setXML(child, i + 1));
						dirArray.add(temp);

					} else {

						if (FilenameUtils.getExtension(child.getName()).equals("jpg")) {
/*
							try {
								ArrayList<ArrayList<String>> arraytemp = CsvParser.getTag(p.getTag(child.getAbsolutePath()));
								TagHistory.saveTag(arraytemp, child.getAbsolutePath());

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
*/
							JsonObject temp = new JsonObject();
							temp.addProperty("nameImage", child.getName());
							temp.addProperty("tag", "Image");
							dirArray.add(temp);

						}
					}

				}
			}

			return dirArray;
		}

		return null;

	}

	/**
	 *
	 * @param path
	 * @return
	 */
	public DefaultMutableTreeNode setDirectoryTree(String path) {

		JsonParser jsonParser = new JsonParser();

		try {

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_16));

			Object obj = jsonParser.parse(reader);

			JsonObject jsonRoot = (JsonObject) obj;
			JsonArray dirArray = (JsonArray) jsonRoot.get("root");
			TaggedTreeNode temp = new TaggedTreeNode("Dossier racine");
			createTree(dirArray, temp);

			return temp;

		} catch (JsonParseException e) {

			JOptionPane.showMessageDialog(null, "The hierarchy json file cannot be open correctly");
			LogFileWritingHandler.handleException(e.getMessage(), e.getStackTrace());

		} catch (FileNotFoundException e) {

			JOptionPane.showMessageDialog(null,	"The hierarchy json file cannot be open/found.\nCheck the conf.properties");
			LogFileWritingHandler.handleException(e.getMessage(), e.getStackTrace());
		} catch (NullPointerException e) {

			JOptionPane.showMessageDialog(null,
					"The arborescence of the file cannot be create.\nCheck the hierarchy json file integrity or check the conf.properties");
			LogFileWritingHandler.handleException(e.getMessage(), e.getStackTrace());
		} catch (ClassCastException e) {

			JOptionPane.showMessageDialog(null, "The hierarchy json file cannot be open correctly");
			LogFileWritingHandler.handleException(e.getMessage(), e.getStackTrace());
		} 

		return null;

	}

	/**
	 *
	 * @param a
	 * @param d
	 */
	public void createTree(JsonArray a, TaggedTreeNode d) {

		if (a.size() > 0)
			for (JsonElement child : a) {

				if (!child.isJsonNull() && ((JsonObject) child).has("nameImage")) {

					TaggedTreeNode temp = new TaggedTreeNode(((JsonObject) child).get("nameImage").getAsString(),
							((JsonObject) child).get("tag").getAsString());
					d.add(temp);

				} else if (!child.isJsonNull()) {

					TaggedTreeNode temp = new TaggedTreeNode(((JsonObject) child).get("name").getAsString(),
							((JsonObject) child).get("tag").getAsString());
					d.add(temp);
					createTree((JsonArray) ((JsonObject) child).get("nextDir"), temp);
				}

			}

	}

}