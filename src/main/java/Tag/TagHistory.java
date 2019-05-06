/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tag;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
            FileWriter writer = new FileWriter(history);
            FileReader reader = new FileReader(history);
            if(history.exists()){
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
                
                writer.write(root.toString());
                writer.close();
            } else {
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
                
                writer.write(root.toString());
                writer.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(TagHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
