/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tag;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaetan
 */
public class TagHistory {
    static public void saveTag(ArrayList<ArrayList<String>> tags, String imagesPath){
        File history = new File("history.json");
        try {
            FileReader reader = new FileReader(history);
            if(history.exists()){
                
                JsonParser parser = new JsonParser();
                JsonElement json = parser.parse(reader);
                JsonObject root = new JsonObject();
                if(json.isJsonNull()){
                    createHistory(history, tags, imagesPath);
                } else {
                    updateTags(history, tags, imagesPath, reader, json);
                }
                reader.close();
            } else {
                createHistory(history, tags, imagesPath);
            }
        } catch (IOException ex) {
            Logger.getLogger(TagHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static private void createHistory(File history, ArrayList<ArrayList<String>> tags, String imagesPath){
            JsonObject root = new JsonObject();
                root.addProperty("type", "IntermediateRegister");
                root.addProperty("imageCounter", 1);
                JsonArray content = new JsonArray();
                JsonArray tag = new JsonArray();
                JsonArray tagArray = new JsonArray();
                
                for(ArrayList<String> elem : tags){
                    for(String str : elem){
                        tag.add(str);
                    }
                    tagArray.addAll(tag);
                }
                
                JsonObject temp = new JsonObject();
                temp.addProperty("path", imagesPath);
                temp.add("tags", tagArray);
                content.add(temp);
                
                root.add("content", content);
                
                FileWriter writer;
                try {
                    writer = new FileWriter(history);
                    writer.write(root.toString());
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(TagHistory.class.getName()).log(Level.SEVERE, null, ex);
                }
               
    }
    
    static private void updateTags(File history, ArrayList<ArrayList<String>> tags, String imagesPath, FileReader reader, JsonElement json){
        JsonObject root = json.getAsJsonObject();
        int counter = root.get("imageCounter").getAsInt();
        boolean test = false;

        JsonArray content = root.getAsJsonArray("content");
        JsonObject tmp = new JsonObject();
        JsonArray tag = new JsonArray();
        JsonArray tagArray = new JsonArray();;

        for(int i = 0; i < content.size(); i++){
            tmp = content.get(i).getAsJsonObject();
            String path = tmp.get("path").getAsString();

            if(path.equals(imagesPath)){
                tmp.remove("tags");
                test = true;
                break;
            }

        }

        for(ArrayList<String> elem : tags){
            for(String str : elem){
                tag.add(str);
            }
            tagArray.addAll(tag);
        }

        if(test){
            tmp.add("tags", tagArray);
        } else {
            tmp.add("tags", tagArray);
            tmp.addProperty("path", imagesPath);

            content.add(tmp);

            root.remove("imageCounter");
            root.addProperty("imageCounter", counter+1);
            root.add("content", content);

        }
        
        FileWriter writer;
        try {
            writer = new FileWriter(history);
            writer.write(root.toString());
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(TagHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}
