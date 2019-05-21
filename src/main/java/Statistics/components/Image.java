package Statistics.components;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.StreamSupport;

/**
 * Represente une image de la banque de donnee avec tous ses attributs
 * @author Groupe PRO B-9
 */
public class Image {

    private String camera;
    private String date;                // xxxx-xx-xx form
    private String sequence;            // hh:mm form 
    private Month month;
    private int day;
    private int hour;
    private final ArrayList<Tag> tags;  // Tags saved in this image

    /**
     * Renvoie une representation du path sous forme d'un tableau, chaque
     * element du tableau est un sous-dossier
     * @param pathString le chemin d'acces a separer
     * @return le tableau genere
     */
    private static String[] splitPath(String pathString) {
        Path path = Paths.get(pathString);
        return StreamSupport.stream(path.spliterator(), false).map(Path::toString)
                .toArray(String[]::new);
    }

    /**
     * Constructeur
     * @param path le chemin d'acces de l'image
     * @param tags la liste des tags enregistres sur l'image
     */
    public Image(String path, ArrayList<Tag> tags) {
        pathDecomposition(path);
        this.tags = tags;
    }

    /**
     * Enregistrement des attributs du path dans les attributs de la classe Image
     * @param path le chemin d'acces a decortiquer
     */
    private void pathDecomposition(String path) {
        try {

            String[] path_sep = splitPath(path);
            this.camera = path_sep[0];
            this.date = path_sep[1];
            this.sequence = path_sep[0] + "/" + path_sep[1] + "/" + path_sep[4] + ":" + path_sep[5];
            String[] dateParts = date.split("-");
            this.month = Month.values()[Integer.parseInt(dateParts[1])-1];
            this.day = Integer.parseInt(dateParts[2]);
            this.hour = Integer.parseInt(path_sep[4]);

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

    public int getDay() {
        return this.day;
    }
    
    public int getHour() {
        return this.hour;
    }
          
    public boolean hasTags(){
        return !tags.isEmpty();
    }

    @Override
    public String toString() {
        return camera + " " + date + " " + sequence + " :    " + tags.toString() + "\n";
    }
}
