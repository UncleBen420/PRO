package meteoAPI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

/**
 * Class MeteoAPI parser de fichier Json, elle parcourt toutes les dates des images, recupere les informations
 * necessaire afin de creer des objets representant les differentes donnees voulues (MeteoPerDay).
 *
 * @author Groupe PRO B-9
 */
public class MeteoAPI {

    private List<MeteoPerDay> listMeteoPerDay = new ArrayList<>();

    /**
     * Methode recuperant tout les informations meteo fournies par l'API
     *
     * @return listMeteoPerDay
     */
    public List<MeteoPerDay> getList() {
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader("jsonFiles/meteo.json")) {
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
        return listMeteoPerDay;
    }

    /**
     * Methode recuperant tout les informations meteo fournies par l'API en lui appliquant un filtre
     * avec une condition meteorologique
     *
     * @return listMeteoPerDay
     */
    public List<MeteoPerDay> getListFiltreSummary(TYPEMETEO filtre) {
        boolean firstMeasureOfMeteo;
        int counterOfDays = 0;
        int counterOfHours;
        List<MeteoPerDay> listDatesPerFiltre = new ArrayList<>();
        List<MeteoPerDay> listAllDates = getList();

        for (MeteoPerDay day : listAllDates) {
            counterOfHours = 0;
            firstMeasureOfMeteo = true;
            for (String s : day.getMeteo()) {
                if (firstMeasureOfMeteo) {
                    listDatesPerFiltre.add(new MeteoPerDay(day.getDate()));
                    firstMeasureOfMeteo = false;
                }
                if (s.equals(filtre.toString())) {
                    listDatesPerFiltre.get(counterOfDays).addMeteo(s);
                } else {
                    listDatesPerFiltre.get(counterOfDays).addMeteo("-1");
                }
                listDatesPerFiltre.get(counterOfDays).addTemperature(day.getTemperature().get(counterOfHours));
                ++counterOfHours;
            }
            ++counterOfDays;
        }
        return listDatesPerFiltre;
    }

    /**
     * Methode recuperant tout les informations meteo fournies par l'API en lui appliquant un filtre
     * avec des temperatures
     *
     * @return listMeteoPerDay
     */
    public List<MeteoPerDay> getListFiltreTemperature(double min, double max) {
        boolean firstMeasureOfTemperature;
        int counterOfDays = 0;
        int counterOfHours;
        List<MeteoPerDay> listMeteoPerDay = new ArrayList<>();
        List<MeteoPerDay> listAllDates = getList();

        for (MeteoPerDay day : listAllDates) {
            counterOfHours = 0;
            firstMeasureOfTemperature = true;
            for (Double s : day.getTemperature()) {
                if (firstMeasureOfTemperature) {
                    listMeteoPerDay.add(new MeteoPerDay(day.getDate()));
                    firstMeasureOfTemperature = false;
                }
                if (min <= s && s <= max) {
                    listMeteoPerDay.get(counterOfDays).addTemperature(s);
                } else {
                    listMeteoPerDay.get(counterOfDays).addTemperature(99.);
                }
                listMeteoPerDay.get(counterOfDays).addMeteo(day.getMeteo().get(counterOfHours));
                ++counterOfHours;
            }
            ++counterOfDays;
        }
        return listMeteoPerDay;
    }

    /**
     * Methode recuperant toutes informations sur le Json pour une date
     *
     * @param date
     */
    private void parseDateObject(JsonObject date) {
        MeteoPerDay met = new MeteoPerDay(date.get("date").getAsString());
        JsonArray hourArray = (JsonArray) ((JsonObject) ((JsonObject) date.get("properties")).get("hourly")).get("data");
        hourArray.forEach(hour -> parseHourObject(met, (JsonObject) hour));
        listMeteoPerDay.add(met);
    }

    /**
     * Methode recuperant pour une date donnee, toutes les informations pour chaque heure du jour
     *
     * @param met
     * @param hour
     */
    private void parseHourObject(MeteoPerDay met, JsonObject hour) {
        met.addMeteo(hour.get("summary").getAsString());
        met.addTemperature(hour.get("temperature").getAsDouble());
    }
}