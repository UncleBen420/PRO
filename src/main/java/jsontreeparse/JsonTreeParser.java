package jsontreeparse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Arrays;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.io.FilenameUtils;
import com.google.gson.*;

public class JsonTreeParser {

	public void createXML(File rootDirectory) {
		try {

			FileWriter file = new FileWriter("src/jsonFile.json");
			JsonArray tree = setXML(rootDirectory);
			JsonObject temp = new JsonObject();

			temp.add("root", tree);

			file.write(temp.toString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public JsonArray setXML(File rootDirectory) {

		if (rootDirectory.isDirectory()) {

			JsonArray dirArray = new JsonArray();

			File[] childrenDir = rootDirectory.listFiles();
			if (childrenDir != null) {
				Arrays.sort(childrenDir);
				
				for (File child : childrenDir) {

					if (child.isDirectory()) {

						JsonObject temp = new JsonObject();
						temp.addProperty("name", child.getName());
						temp.add("nextDir", setXML(child));
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
			DefaultMutableTreeNode temp = new DefaultMutableTreeNode();
			createTree(dirArray, temp);

			return temp;

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void createTree(JsonArray a, DefaultMutableTreeNode d) {

		if (a.size() > 0)
			for (JsonElement child : a) {

				if (!child.isJsonNull() && ((JsonObject) child).has("image")) {

					DefaultMutableTreeNode temp = new DefaultMutableTreeNode(((JsonObject) child).get("image").getAsString());
					d.add(temp);

				} else if (!child.isJsonNull()) {

					DefaultMutableTreeNode temp = new DefaultMutableTreeNode(((JsonObject) child).get("name").getAsString());
					d.add(temp);
					createTree((JsonArray) ((JsonObject) child).get("nextDir"), temp);
				}

			}

	}

}
