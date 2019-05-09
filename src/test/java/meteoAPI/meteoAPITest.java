package meteoAPI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gaetan
 */
public class meteoAPITest {

    /**
     *
     * @throws FileNotFoundException
     */
    @BeforeEach
    public void recuperationFichier() throws FileNotFoundException {
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader("src/meteo.json")) {
            Object obj = jsonParser.parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws FileNotFoundException
     */
    @Test
    public void lectureFichier() throws FileNotFoundException {
        MeteoAPI meteo = new MeteoAPI();

        meteo.getList();
    }

    /**
     *
     */
    @Test
    public void creationdelameteodu23fevrier () {
        MeteoAPI meteo = new MeteoAPI();

        List<MeteoPerDay> listMeteo = meteo.getList();

        Date date = null;
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        try {
            date = df.parse("2017-02-23");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(date, listMeteo.get(0).getDate());
    }

    /**
     *
     */
    @Test
    public void recuperationDeToutesLesDateOuIlFaisaitBeau () {
        MeteoAPI meteo = new MeteoAPI();

        List<MeteoPerDay> listMeteo = meteo.getListFiltreSummary(TYPEMETEO.DEGAGE);

        for (MeteoPerDay met : listMeteo) {
            System.out.println(met.getDate());
            for (String s : met.getMeteo()) {
                System.out.println(s);
            }
        }
    }

    /**
     *
     */
    @Test
    public void recuperationDeToutesLesTemperatures() {
        MeteoAPI meteo = new MeteoAPI();

        List<MeteoPerDay> listMeteo = meteo.getList();

        for (MeteoPerDay met : listMeteo) {
            System.out.println(met.getDate());
            for (Double s : met.getTemperature()) {
                System.out.println(s);
            }
        }
    }
}
