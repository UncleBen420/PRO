/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics.parser;

import Statistics.handler.StatisticsHandler;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.*;
import java.util.ArrayList;

import Statistics.components.Image;
import Statistics.components.Month;
import Statistics.components.Tag;

/**
 * Parse le fichier history qui contient les informations json de la banque d'images
 * @author Groupe PRO B-9
 */
public class StatParser {
 
    private static final String HISTORIC = "jsonFiles/history.json";
    private final StatisticsHandler statHandler;
    private String currentSequence;

    
    public StatParser(StatisticsHandler statHandler) {
        this.statHandler = statHandler;
        this.currentSequence = "";
    }
     
    /**
     * Parsage du fichier history
     * @return un objet JsonParse avec les informations generees
     */
    public JsonParser parseFile() {
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(HISTORIC)) {

            Object obj = jsonParser.parse(reader);

            JsonObject images = (JsonObject) obj;           
            JsonArray content = (JsonArray) images.get("content");
            parseImageContent(content);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return jsonParser;
    }

    /**
     * Parse le contenu d'un JsonArray et remplit l'objet statHandler qui contiendra
     * les informations necessaires a la generation des graphes
     * @param content le JsonArray a parser
     */
    private void parseImageContent(JsonArray content) {

        for (Object image : content) {
            JsonObject jimage = (JsonObject) image;
            
            String path = (String) jimage.get("path").getAsString();
            JsonArray tags = (JsonArray) jimage.get("tags");
            
           ArrayList<Tag> tagsStructure = parseContentTags(tags);         
           Image imageStructure = new Image(path, tagsStructure);
           
            if (!currentSequence.equals(imageStructure.getSequence())) {
                currentSequence = imageStructure.getSequence();
                statHandler.addNbSequences(1);
                if (imageStructure.hasTags()) {
                    statHandler.addNbTaggedSequences(1);
                }
            }

            if (imageStructure.hasTags()) {
                //statHandler.addNbTaggedImages(1);
                System.out.println("finally some tags");
                statHandler.countCameraObservation(imageStructure.getCamera(), tagsStructure.size());
                statHandler.countDaysObservation(imageStructure.getDate(), tagsStructure.size());
                statHandler.countSequenceObservation(imageStructure.getSequence(), tagsStructure.size());
                statHandler.countMonthlyObservation(imageStructure.getMonth(), tagsStructure.size());
                statHandler.countDailyObservation(imageStructure.getMonth(), imageStructure.getDay(), tagsStructure.size());
                statHandler.countHourlyObservation(imageStructure.getMonth(), imageStructure.getDay(), imageStructure.getHour(), tagsStructure.size());

                statHandler.addNbAnimals(tagsStructure.size());

                for (Tag tag : tagsStructure) {
                    statHandler.countMonthlyObservationsByAnimalType(tag.getAnimalType(), imageStructure.getMonth());
                    statHandler.countDailyObservationsByAnimalType(tag.getAnimalType(), imageStructure.getMonth(), imageStructure.getDay());
                    statHandler.countHourlyObservationsByAnimalType(tag.getAnimalType(), imageStructure.getMonth(), imageStructure.getDay(), imageStructure.getHour());
                    statHandler.countTotalObservationsByAnimalType(tag.getAnimalType());
                }

                statHandler.addImage(imageStructure);
                
            } 
            statHandler.addNbImages(1);
        }

    }

    /**
     * Parse le JsonArray des tags
     * @param tags le JsonArray des tags
     * @return un ArrayList des tags recuperes
     */
    private ArrayList<Tag> parseContentTags(JsonArray tags) {
        
        ArrayList<Tag> result = new ArrayList<>();
        for (Object tag : tags) {
            JsonArray jtag = (JsonArray) tag;
            Tag tagStructure = new Tag();
            
            tagStructure.setTypeAnimal(jtag.get(1).getAsString());
            tagStructure.setSize(jtag.get(2).getAsDouble());
            tagStructure.setIsMale(jtag.get(3).getAsBoolean());
            tagStructure.setIsEnteringTunnel(jtag.get(4).getAsBoolean());

            result.add(tagStructure);
        }
        return result;
    }
    
}
