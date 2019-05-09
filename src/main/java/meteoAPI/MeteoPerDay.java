package meteoAPI;

import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gaetan
 */
public class MeteoPerDay {
    private Date date;
    private List<String> Meteo = new ArrayList<>();
    private List<Double> Temperature = new ArrayList<>();
    private List<Boolean> rain = new ArrayList<>();

    /**
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
     *
     * @param date
     */
    public MeteoPerDay(Date date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @return
     */
    public List<String> getMeteo() {
        return Meteo;
    }

    /**
     *
     * @param summary
     */
    public void addMeteo(Object summary) {
        Meteo.add((String) summary);
    }

    /**
     *
     * @return
     */
    public List<Double> getTemperature() {
        return Temperature;
    }

    /**
     *
     * @param temperature
     */
    public void addTemperature(Object temperature) {
        Temperature.add((Double) temperature);
    }

    /**
     *
     * @return
     */
    public List<Boolean> getRainInfo() {
        return rain;
    }
    
    /**
     *
     * @param rainInfo
     */
    public void addRain(Boolean rainInfo) {
        rain.add(rainInfo);
    }

}
