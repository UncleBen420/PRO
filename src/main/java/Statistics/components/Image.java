/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics.components;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.StreamSupport;

/**
 *
 * @author Marion
 */
public class Image {

    private String camera;
    private String date;                // xxxx-xx-xx form
    private String sequence;            // hh:mm form 
    private Month month;
    private final ArrayList<Tag> tags;  // Tags saved in this image

    private static String[] splitPath(String pathString) {
        Path path = Paths.get(pathString);
        return StreamSupport.stream(path.spliterator(), false).map(Path::toString)
                .toArray(String[]::new);
    }

    public Image(String path, ArrayList<Tag> tags) {
        pathDecomposition(path);
        this.tags = tags;
    }

    private void pathDecomposition(String path) {
        try {

            String[] path_sep = splitPath(path);
            this.camera = path_sep[0];
            this.date = path_sep[1];
            this.sequence = path_sep[0] + "/" + path_sep[1] + "/" + path_sep[4] + ":" + path_sep[5];
            String[] dateParts = date.split("-");
            this.month = Month.values()[Integer.parseInt(dateParts[1])-1];

        } catch (Exception e) {
            System.out.println("Error: not valid path");
        }
    }

    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    public String getCamera() {
        return this.camera;
    }

    public String getDate() {
        return this.date;
    }

    public String getSequence() {
        return this.sequence;
    }

    public Month getMonth() {
        return this.month;
    }

    @Override
    public String toString() {
        return camera + " " + date + " " + sequence + " :    " + tags.toString() + "\n";
    }
}
