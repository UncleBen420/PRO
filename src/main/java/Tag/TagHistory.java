
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
 * Implemente les fonctions pour creer un fichier JSON contenant les tags
 * ajoutes aux images pour pouvoir effectuer les statistiques sans parcourir
 * toutes les images à chaque fois
 *
 * @author Groupe PRO B-9
 */
public class TagHistory {
    
    private static ArrayList<String> paths;

    /**
     * Enregistre ou met à jour les tags pour une ou plusieurs images dans un
     * fichier history.json
     *
     * @param tags Liste des tags
     * @param imagesPath Chemin de l'image ou du dossier
     */
    static public void saveTag(ArrayList<ArrayList<String>> tags, String imagesPath) {
        File history = new File("jsonFiles/history.json");
        File file = new File(imagesPath);
        try {
            if (file.isFile()) {

                if (history.exists()) {
                    try (FileReader reader = new FileReader(history)) {
                        JsonParser parser = new JsonParser();
                        JsonElement json = parser.parse(reader);
                        if (json.isJsonNull()) {
                            createHistory(history, tags, getRelativePath(imagesPath));
                        } else {
                            updateTags(history, tags, getRelativePath(imagesPath), json);
                        }
                    }
                } else {
                    createHistory(history, tags, getRelativePath(imagesPath));
                }
            } else if (file.isDirectory()) {
                File[] list = file.listFiles();
                for (File f : list) {
                    if (getFileExtension(f).equals("jpg")) {

                        if (history.exists()) {
                            try (FileReader reader = new FileReader(history)) {
                                JsonParser parser = new JsonParser();
                                JsonElement json = parser.parse(reader);
                                if (json.isJsonNull()) {
                                    createHistory(history, tags, getRelativePath(f.getAbsolutePath()));
                                } else {
                                    updateTags(history, tags, getRelativePath(f.getAbsolutePath()), json);
                                }
                            }
                        } else {
                            createHistory(history, tags, getRelativePath(f.getAbsolutePath()));
                        }
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(TagHistory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Cree la base du fichier json avec les premier tags
     *
     * @param history Fichier d'historique
     * @param tags Liste des tags
     * @param imagesPath Chemin de l'image
     */
    static private void createHistory(File history, ArrayList<ArrayList<String>> tags, String imagesPath) {
        JsonObject root = new JsonObject();
        root.addProperty("type", "IntermediateRegister");
        root.addProperty("imageCounter", 1);
        JsonArray content = new JsonArray();

        JsonArray tagArray = new JsonArray();

        for (ArrayList<String> elem : tags) {
            JsonArray tag = new JsonArray();
            if(elem != null){
                for (String str : elem) {
                    tag.add(str);
                }
            }
            tagArray.add(tag);
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

    /**
     * Met a jour les tags pour une image deja enregistree
     *
     * @param history fichier d'historique
     * @param tags liste des tags
     * @param imagesPath chemin de l'image
     * @param json element racine ou ajouter les tags
     */
    static private void updateTags(File history, ArrayList<ArrayList<String>> tags, String imagesPath, JsonElement json) {
        JsonObject root = json.getAsJsonObject();
        int counter = root.get("imageCounter").getAsInt();
        boolean test = false;

        JsonArray content = root.getAsJsonArray("content");
        JsonObject tmp = new JsonObject();

        JsonArray tagArray = new JsonArray();

        for (int i = 0; i < content.size(); i++) {
            tmp = content.get(i).getAsJsonObject();
            String path = tmp.get("path").getAsString();

            if (path.equals(imagesPath)) {
                tmp.remove("tags");
                test = true;
                break;
            }

        }

        for (ArrayList<String> elem : tags) {
            JsonArray tag = new JsonArray();
            for (String str : elem) {
                tag.add(str);
            }
            tagArray.add(tag);
        }

        if (test) {
            tmp.add("tags", tagArray);
        } else {
            tmp.add("tags", tagArray);
            tmp.addProperty("path", imagesPath);

            content.add(tmp);

            root.remove("imageCounter");
            root.addProperty("imageCounter", counter + 1);
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

    /**
     * Recupere l'extension d'un fichier
     *
     * @param file Fichier
     * @return Retourne l'extension
     */
    static private String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');
        return fileName.substring(lastDot + 1);
    }

    /**
     * Retourne le chemin relatif d'une image
     *
     * @param path chemin absolu de l'image
     * @return chemin relatif
     */
    static public String getRelativePath(String path) {
        return path.substring(path.indexOf("Cam�ra", 0));
    }
    
    static public boolean findTag(String path){
        path = getRelativePath(path);
        
        return paths.contains(path);
    }
    
    static public void getPaths(){
        paths = new ArrayList<>();
        
        File history = new File("jsonFiles/history.json");
        try {

            if (history.exists()) {
                try (FileReader reader = new FileReader(history)) {
                    JsonParser parser = new JsonParser();
                    JsonElement json = parser.parse(reader);
                    if (!json.isJsonNull()) {
                        JsonObject root = json.getAsJsonObject();

                        JsonArray content = root.getAsJsonArray("content");
                        JsonObject tmp;

                        for (int i = 0; i < content.size(); i++) {
                            tmp = content.get(i).getAsJsonObject();
                            String path = tmp.get("path").getAsString();
                            JsonArray tags = tmp.getAsJsonArray("tags");
                            if(tags != null && tags.size() > 0)
                                paths.add(path);
                        }

                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(TagHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
