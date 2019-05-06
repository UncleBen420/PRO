package meteoAPI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

public class MeteoAPI {

    private List<MeteoPerDay> list = new ArrayList<>();

    public List<MeteoPerDay> getList() {
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader("src/meteo.json")) {
            Object obj = jsonParser.parse(reader);
            JsonArray dates = (JsonArray) ((JsonObject) ((JsonObject) obj).get("meteo")).get("dates");
            dates.forEach(date -> parseDateObject((JsonObject) date));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void parseDateObject(JsonObject date) {
        MeteoPerDay met = new MeteoPerDay(date.get("date").getAsString());
        JsonArray hourArray = (JsonArray) ((JsonObject) ((JsonObject) date.get("properties")).get("hourly")).get("data");
        hourArray.forEach(hour -> parseHourObject(met, (JsonObject) hour));
        list.add(met);
    }

    public List<MeteoPerDay> getList(String filtre) {
        boolean test;
        int i = 0;
        List<MeteoPerDay> meteoPerFiltre = new ArrayList<>();
        List<MeteoPerDay> meteoProjet = getList();
        MeteoPerDay met = null;

        for (MeteoPerDay day : meteoProjet) {

            test = true;
            for (String s : day.getMeteo()) {
                if (test) {
                    met = new MeteoPerDay(day.getDate());
                    meteoPerFiltre.add(met);
                    test = false;
                }
                if (s.equals(filtre)) {
                    meteoPerFiltre.get(i).addMeteo(s);
                } else {
                    meteoPerFiltre.get(i).addMeteo("-1");
                }
            }
            ++i;
        }

        return meteoPerFiltre;
    }

    private void parseHourObject(MeteoPerDay met, JsonObject hour) {
        met.addMeteo(hour.get("summary").getAsString());
    }
}