package meteoAPI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

/**
 * @author gaetan
 */
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

    public List<MeteoPerDay> getListFiltreSummary(TYPEMETEO filtre) {
        boolean firstMeasureOfMeteo;
        int counterOfDays = 0;
        int counterOfHours;
        List<MeteoPerDay> meteoPerFiltre = new ArrayList<>();
        List<MeteoPerDay> meteoProjet = getList();

        for (MeteoPerDay day : meteoProjet) {
            counterOfHours = 0;
            firstMeasureOfMeteo = true;
            for (String s : day.getMeteo()) {
                if (firstMeasureOfMeteo) {
                    meteoPerFiltre.add(new MeteoPerDay(day.getDate()));
                    firstMeasureOfMeteo = false;
                }
                if (s.equals(filtre.toString())) {
                    meteoPerFiltre.get(counterOfDays).addMeteo(s);
                } else {
                    meteoPerFiltre.get(counterOfDays).addMeteo("-1");
                }
                meteoPerFiltre.get(counterOfDays).addTemperature(day.getTemperature().get(counterOfHours));
                meteoPerFiltre.get(counterOfDays).addRain(day.getRainInfo().get(counterOfHours));
                ++counterOfHours;
            }
            ++counterOfDays;
        }
        return meteoPerFiltre;
    }

    public List<MeteoPerDay> getListFiltreTemperature(double min, double max) {
        boolean firstMeasureOfTemperature;
        int counterOfDays = 0;
        int counterOfHours;
        List<MeteoPerDay> meteoPerFiltre = new ArrayList<>();
        List<MeteoPerDay> meteoProjet = getList();

        for (MeteoPerDay day : meteoProjet) {
            counterOfHours = 0;
            firstMeasureOfTemperature = true;
            for (Double s : day.getTemperature()) {
                if (firstMeasureOfTemperature) {
                    meteoPerFiltre.add(new MeteoPerDay(day.getDate()));
                    firstMeasureOfTemperature = false;
                }
                if (min <= s && s <= max) {
                    meteoPerFiltre.get(counterOfDays).addTemperature(s);
                } else {
                    meteoPerFiltre.get(counterOfDays).addTemperature(99.);
                }
                meteoPerFiltre.get(counterOfDays).addMeteo(day.getMeteo().get(counterOfHours));
                meteoPerFiltre.get(counterOfDays).addRain(day.getRainInfo().get(counterOfHours));
                ++counterOfHours;
            }
            ++counterOfDays;
        }
        return meteoPerFiltre;
    }

    private void parseDateObject(JsonObject date) {
        MeteoPerDay met = new MeteoPerDay(date.get("date").getAsString());
        JsonArray hourArray = (JsonArray) ((JsonObject) ((JsonObject) date.get("properties")).get("hourly")).get("data");
        hourArray.forEach(hour -> parseHourObject(met, (JsonObject) hour));
        list.add(met);
    }

    private void parseHourObject(MeteoPerDay met, JsonObject hour) {
        met.addMeteo(hour.get("summary").getAsString());
        met.addTemperature(hour.get("temperature").getAsDouble());
        met.addRain(hour.has("precipType"));
    }
}