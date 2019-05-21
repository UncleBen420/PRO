package properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Cette classe permet de parser le fichier conf.properties
 * @author Groupe PRO B-9
 */
public class PropertiesHandler {
	
    /**
     * parse les propriétés du fichier.
     * @return les propriétés du fichier
     */
    static public Properties parseProperties() {
		Properties properties = new Properties();
		FileReader fr = null;
		try {
			fr = new FileReader("conf.properties");
			properties.load(fr);
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return properties;
	}

}
