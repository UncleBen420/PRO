package jsontreeparse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.io.FilenameUtils;
import com.google.gson.*;

import JTreeManager.TaggedTreeNode;
import Tag.Parser;
import Tag.TagHistory;
import errormessage.ErrorMessage;
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
    public void parseHierarchyTag(){
		
			Properties properties = PropertiesHandler.parseProperties();
			hierarchyTag = ((String)properties.get("hierarchyTag")).split("/");	
	}

    /**
     *
     * @param rootDirectory
     */
    
    private static int current = 0;
    
    public void createXML(File rootDirectory) {
		try {
			
			int i = 0;
			current = 0;
			
			parseHierarchyTag();
			Properties properties = PropertiesHandler.parseProperties();
			
			int max = fileCount(Paths.get(rootDirectory.getAbsolutePath()));
			
			ErrorMessage progressBarDialog = new ErrorMessage();
			progressBarDialog.doJob(max , "Aucun fichier Treefile.json trouver.\nCreation du fichier Treefile.json");
			

			FileWriter file = new FileWriter((new File(properties.getProperty("JsonBankPath")).getAbsolutePath()));
			JsonArray tree = new JsonArray();
			JsonObject temp = new JsonObject();
			try {
				
				
				List<ExecutorService> threads = new ArrayList<>();
				List<JsonArray> o = new ArrayList<>();
				List<Future<JsonArray>> results = new ArrayList<>();
				
				File[] childrenDir = rootDirectory.listFiles();
				if (childrenDir != null) {
					Arrays.sort(childrenDir);
					
					for (File child : childrenDir) {

						if (child.isDirectory()) {
							
						    ExecutorService es = Executors.newSingleThreadExecutor();
						    Future<JsonArray> result = es.submit(new Callable<JsonArray>() {
						    	
						        @Override
								public JsonArray call() throws Exception {
						        	JsonArray dirArray = new JsonArray();
						        	JsonObject temp = new JsonObject();
						        						       						 
									temp.addProperty("name", child.getName());
									temp.addProperty("tag", hierarchyTag[i]);
									temp.add("nextDir", setXML(child,i+1,progressBarDialog));
									
									dirArray.add(temp);
						        	return dirArray;
						        }
						    });
						    results.add(result);   
							
			            };
							
						}
					}
					for(ExecutorService t : threads) {
		
							try {
								t.awaitTermination(1, TimeUnit.HOURS);
								t.shutdown();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

					
					for(Future<JsonArray> r : results) {
						try {
					    	o.add(r.get());
					    } catch (Exception e) {
					        // failed
					    }
					    
					}
					
					for(JsonArray js : o) {
						tree.add(js);
					}
					
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
			
			//progressBarDialog.done();

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
    public JsonArray setXML(File rootDirectory, int i, ErrorMessage er) throws ArrayIndexOutOfBoundsException {

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
						temp.add("nextDir", setXML(child,i+1,er));
						dirArray.add(temp);

					} else {

						if (FilenameUtils.getExtension(child.getName()).equals("jpg")) {
							
							current++;
							System.out.println(current);
							er.updateBar(current);

							JsonObject temp = new JsonObject();
							temp.addProperty("nameImage", child.getName());
							temp.addProperty("tag", "Image");
							
							try {
								
			
								File test = new File(child.getAbsolutePath());

								if(p.isTagged(test)) {
								
									
									ArrayList<ArrayList<String>> arraytemp = new ArrayList<ArrayList<String>>();

									arraytemp.add(p.getTag(child.getAbsolutePath()));
									TagHistory.saveTag(arraytemp, child.getAbsolutePath());
								
							}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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

			FileReader reader = new FileReader(path);

			// lecture du fichier
			Object obj;

			obj = jsonParser.parse(reader);

			JsonObject personne = (JsonObject) obj;
			JsonArray dirArray = (JsonArray) personne.get("root");
			TaggedTreeNode temp = new TaggedTreeNode("Dossier racine");
			for(int i = 0; i < dirArray.size(); i++) {
				
				createTree(dirArray.get(i).getAsJsonArray(), temp);
				
			}
			

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

    /**
     *
     * @param a
     * @param d
     */
    public void createTree(JsonArray a, TaggedTreeNode d) {

		if (a.size() > 0)
			for (JsonElement child : a) {

				if (!child.isJsonNull() && ((JsonObject) child).has("nameImage")) {

					TaggedTreeNode temp = new TaggedTreeNode(((JsonObject) child).get("nameImage").getAsString(),((JsonObject) child).get("tag").getAsString());
					d.add(temp);

				} else if (!child.isJsonNull()) {

					TaggedTreeNode temp = new TaggedTreeNode(((JsonObject) child).get("name").getAsString(),((JsonObject) child).get("tag").getAsString());
					d.add(temp);
					createTree((JsonArray) ((JsonObject) child).get("nextDir"), temp);
				}

			}

	}
    
    public int fileCount(Path dir) throws IOException { 
        return (int) Files.walk(dir).parallel().filter(p -> !p.toFile().isDirectory()).count();
    }

}
