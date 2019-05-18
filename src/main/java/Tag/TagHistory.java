/**
 * PRO
 * Authors: Bacso
 * File: TagHistory.java
 * IDE: NetBeans IDE 11
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
 * Implémente les fonctions pour créer un fichier JSON contenant les tags
 * ajoutés aux images pour pouvoir effectuer les statistiques sans parcourir
 * toutes les images à chaque fois
 *
 * @author gaetan
 */
public class TagHistory {

    /**
     * Enregistre ou met à jour les tags pour une ou plusieurs images dans un
     * fichier history.json
     *
     * @param tags Liste des tags
     * @param imagesPath Chemin de l'image ou du dossier
     */
    static public void saveTag(ArrayList<ArrayList<String>> tags, String imagesPath) {
        File history = new File("history.json");
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
     * Crée la base du fichier json avec les premier tags
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
            for (String str : elem) {
                tag.add(str);
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
     * Met à jour les tags pour une image déjà enregistrée
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
     * Récupère l'extension d'un fichier
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
    static private String getRelativePath(String path) {
        return path.substring(path.indexOf("Cam�ra", 0));
    }
}
