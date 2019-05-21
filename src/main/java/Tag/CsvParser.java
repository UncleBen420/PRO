
package Tag;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe decoupant les tags en liste de String
 *
 * @author Groupe PRO B-9
 */
public class CsvParser {

    /**
     * Découpe les tags en liste de String
     *
     * @param tags Tags de l'image
     * @return Liste des tags decoupes
     */
    static public ArrayList<ArrayList<String>> getTag(ArrayList<String> tags) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

        
        
        for (String str : tags) {
            if(str.equals("")){
                result.add(new ArrayList<String>());
            } else {
                String[] splits = str.split(";");
                result.add(new ArrayList<String>(Arrays.asList(splits)));
            }
        }

        return result;
    }
}
