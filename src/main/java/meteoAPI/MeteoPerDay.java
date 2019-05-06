package meteoAPI;

import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeteoPerDay {
    private Date date;
    private List<String> listMeteo = new ArrayList<>();

    public MeteoPerDay(String date) {

        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        try {
            this.date = df.parse((String) date);
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
        return listMeteo;
    }

    public void addMeteo(Object summary) {
        listMeteo.add((String) summary);
    }
}
