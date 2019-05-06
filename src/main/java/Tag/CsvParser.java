/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tag;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author gaetan
 */
public class CsvParser {
    static public ArrayList<ArrayList<String>> getTag(ArrayList<String> tags){
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        
        for(String str : tags){
            String[] splits = str.split(";");
            result.add(new ArrayList<String>(Arrays.asList(splits)));
        }
        
        return result;
    }
}
