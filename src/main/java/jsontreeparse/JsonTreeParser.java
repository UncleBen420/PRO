package jsontreeparse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Arrays;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.io.FilenameUtils;
import com.google.gson.*;

import JTreeManager.TaggedTreeNode;

public class JsonTreeParser {
	
	private String[] hierarchyTag;
	
	
	public void parseHierarchyTag(){
		
			Properties properties = new Properties();
			FileReader fr = null;
			try {
				fr = new FileReader("conf.properties");
				properties.load(fr);
			} catch (FileNotFoundException e) {

				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fr.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			hierarchyTag = ((String)properties.get("hierarchyTag")).split("/");
			for(String s : hierarchyTag)
			System.out.println(s);
		
		
	}

	public void createXML(File rootDirectory) {
		try {
			
			int i = 0;
			parseHierarchyTag();

			FileWriter file = new FileWriter("src/jsonFile.json");
			JsonArray tree;
			JsonObject temp = new JsonObject();
			try {
				
				tree = setXML(rootDirectory,i);
				
				temp.add("root", tree);
				
			} catch (ArrayIndexOutOfBoundsException e) {
				FileWriter error = new FileWriter("Error.txt");
				error.write(e.toString());
				error.flush();	
				error.close();
			}
			

			file.write(temp.toString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

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
						temp.add("nextDir", setXML(child,i+1));
						dirArray.add(temp);

					} else {

						if (FilenameUtils.getExtension(child.getName()).equals("jpg")) {

							JsonObject temp = new JsonObject();
							temp.addProperty("image", child.getName());
							dirArray.add(temp);

						}
					}

				}
			}

			return dirArray;
		}

		return null;

	}

	public DefaultMutableTreeNode setDirectoryTree(String path) {

		JsonParser jsonParser = new JsonParser();

		try {

			FileReader reader = new FileReader(path);

			// lecture du fichier
			Object obj;

			obj = jsonParser.parse(reader);

			JsonObject personne = (JsonObject) obj;
			JsonArray dirArray = (JsonArray) personne.get("root");
			TaggedTreeNode temp = new TaggedTreeNode("Dossier racine");
			createTree(dirArray, temp);

			return temp;

		} catch (JsonParseException e) {

			JOptionPane.showMessageDialog(null,"The hierarchy json file cannot be open correctly");
			
		} catch (FileNotFoundException e) {

			JOptionPane.showMessageDialog(null,"The hierarchy json file cannot be open found.\nCheck the conf.properties");
		} catch (NullPointerException e) {

			JOptionPane.showMessageDialog(null,"The arborescence of the file cannot be create.\nCheck the hierarchy json file integrity or check the conf.properties");
			
		}
		

		return null;

	}

	public void createTree(JsonArray a, TaggedTreeNode d) {

		if (a.size() > 0)
			for (JsonElement child : a) {

				if (!child.isJsonNull() && ((JsonObject) child).has("image")) {

					TaggedTreeNode temp = new TaggedTreeNode(((JsonObject) child).get("image").getAsString());
					d.add(temp);

				} else if (!child.isJsonNull()) {

					TaggedTreeNode temp = new TaggedTreeNode(((JsonObject) child).get("name").getAsString(),((JsonObject) child).get("tag").getAsString());
					d.add(temp);
					createTree((JsonArray) ((JsonObject) child).get("nextDir"), temp);
				}

			}

	}

}
