/**
 * PRO
 * Authors: Bacso
 * File: CsvParser.java
 * IDE: NetBeans IDE 11
 */
package Tag;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe découpant les tags en liste de String
 *
 * @author Bacso
 */
public class CsvParser {

    /**
     * Découpe les tags en liste de String
     *
     * @param tags Tags de l'image
     * @return Liste des tags découpés
     */
    static public ArrayList<ArrayList<String>> getTag(ArrayList<String> tags) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

        for (String str : tags) {
            String[] splits = str.split(";");
            result.add(new ArrayList<String>(Arrays.asList(splits)));
        }

        return result;
    }
}
