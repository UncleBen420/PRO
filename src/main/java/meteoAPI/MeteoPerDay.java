package meteoAPI;

import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeteoPerDay {
    private Date date;
    private List<String> Meteo = new ArrayList<>();
    private List<Double> Temperature = new ArrayList<>();
    private List<Boolean> rain = new ArrayList<>();

    public MeteoPerDay(String date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.date = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public MeteoPerDay(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getMeteo() {
        return Meteo;
    }

    public void addMeteo(Object summary) {
        Meteo.add((String) summary);
    }

    public List<Double> getTemperature() {
        return Temperature;
    }

    public void addTemperature(Object temperature) {
        Temperature.add((Double) temperature);
    }

    public List<Boolean> getRainInfo() {
        return rain;
    }
    
    public void addRain(Boolean rainInfo) {
        rain.add(rainInfo);
    }

}
