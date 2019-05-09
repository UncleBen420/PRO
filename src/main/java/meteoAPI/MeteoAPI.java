package meteoAPI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

/**
 *
 * @author gaetan
 */
public class MeteoAPI {

    private List<MeteoPerDay> list = new ArrayList<>();

    /**
     *
     * @return
     */
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

    /**
     *
     * @param filtre
     * @return
     */
    public List<MeteoPerDay> getListFiltreSummary(TYPEMETEO filtre) {
        boolean test;
        int i = 0;
        int j;
        List<MeteoPerDay> meteoPerFiltre = new ArrayList<>();
        List<MeteoPerDay> meteoProjet = getList();
        MeteoPerDay met;


        for (MeteoPerDay day : meteoProjet) {
            j = 0;
            test = true;
            for (String s : day.getMeteo()) {

                if (test) {
                    met = new MeteoPerDay(day.getDate());
                    meteoPerFiltre.add(met);
                    test = false;
                }
                if (s.equals(filtre.toString())) {
                    meteoPerFiltre.get(i).addMeteo(s);
                } else {
                    meteoPerFiltre.get(i).addMeteo("-1");
                }
                meteoPerFiltre.get(i).addTemperature(day.getTemperature().get(j));
                meteoPerFiltre.get(i).addRain(day.getRainInfo().get(j));
                ++j;
            }
            ++i;
        }

        return meteoPerFiltre;
    }

    /**
     *
     * @param filtre
     * @return
     */
    public List<MeteoPerDay> getListFiltreTemperature(Double filtre) {
        boolean test;
        int i = 0;
        int j;
        List<MeteoPerDay> meteoPerFiltre = new ArrayList<>();
        List<MeteoPerDay> meteoProjet = getList();
        MeteoPerDay met;

        for (MeteoPerDay day : meteoProjet) {
            j = 0;
            test = true;
            for (Double s : day.getTemperature()) {

                if (test) {
                    met = new MeteoPerDay(day.getDate());
                    meteoPerFiltre.add(met);
                    test = false;
                }
                if (s.equals(filtre)) {
                    meteoPerFiltre.get(i).addTemperature(s);
                } else {
                    meteoPerFiltre.get(i).addTemperature(99);
                }
                meteoPerFiltre.get(i).addMeteo(day.getMeteo().get(j));
                meteoPerFiltre.get(i).addRain(day.getRainInfo().get(j));
                ++j;
            }
            ++i;
        }

        return meteoPerFiltre;
    }

    private void parseHourObject(MeteoPerDay met, JsonObject hour) {
        met.addMeteo(hour.get("summary").getAsString());
        met.addTemperature(hour.get("temperature").getAsDouble());
        if (hour.has("precipType")) {
            met.addRain(true);
        } else {
            met.addRain(false);
        }
    }
}