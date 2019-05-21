package meteoAPI;

import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe regroupant les informations météo par jour
 *
 * @author Groupe PRO B-9
 */
public class MeteoPerDay {
    private Date date;
    private List<String> Meteo = new ArrayList<>();
    private List<Double> Temperature = new ArrayList<>();

    /**
     * Constructeur de la classe lorsque la date est donnée en string
     *
     * @param date
     */
    public MeteoPerDay(String date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.date = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur de la classe lorsque la date est donnée en format Date
     *
     * @param date
     */
    public MeteoPerDay(Date date) {
        this.date = date;
    }

    /**
     * Methode retournant la date de l'objet
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Methode retournant la liste des condition météorologique pour chaque heure
     *
     * @return meteo
     */
    public List<String> getMeteo() {
        return Meteo;
    }

    /**
     * Methode permettant d'ajouter une condition météorologique à l'objet
     *
     * @param summary
     */
    public void addMeteo(Object summary) {
        Meteo.add((String) summary);
    }

    /**
     * Methode retournant la liste des temperature pour chaque heure
     *
     * @return
     */
    public List<Double> getTemperature() {
        return Temperature;
    }

    /**
     * Methode permettant d'ajouter une température à l'objet
     *
     * @param temperature
     */
    public void addTemperature(Object temperature) {
        Temperature.add((Double) temperature);
    }

    /**
     * Initialise toutes les cases de la liste à la valeur par défaut
     */
    public void setProper() {
        for (int i = Meteo.size(); i < 24; i++) {
            Meteo.add("-1");
        }

        for (int i = Temperature.size(); i < 24; i++) {
            Temperature.add(99.);
        }
    }
}
