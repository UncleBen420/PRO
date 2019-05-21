
package Tag;

import java.util.ArrayList;

import Shapes.*;

/**
 * Classe servant a formater les tags au format CSV (;) et a les envoyer au
 * parser.
 *
 * @author Groupe PRO B-9
 */
public class Tag {
    // Liste des tags formate

    private ArrayList<String> tags = new ArrayList<String>();
    // Parser pour l'ecriture et la lecture des metadata
    private Parser parser = new Parser();

    /**
     * Ajout d'un tag a la liste des tags en le formatant
     *
     * @param tag Tag a formater
     * @param shape Forme a formater
     */
    public void setTag(ArrayList<String> tag, Shapes shape) {
        if (shape == null) {
            tags.add(formatTag(tag));
        } else {
            tags.add(formatTag(tag) + formatShape(shape));
        }

    }

    /**
     * Format un tag en ligne csv
     *
     * @param tag Tag à formater
     * @return Tag formate
     */
    public String formatTag(ArrayList<String> tag) {
        String str = "heigViewer;";

        for (String s : tag) {
            str += s + ";";
        }

        return str;
    }

    /**
     * Formate une forme en ligne CSV
     *
     * @param shape Forme à formter
     * @return Forme formatee
     */
    public String formatShape(Shapes shape) {
        String str = "";

        if (shape instanceof Cercle) {
            str = "circle" + ";" + ((Cercle) shape).getX() + ";" + ((Cercle) shape).getY() + ";" + ((Cercle) shape).getRadius() + ";";
        }

        if (shape instanceof Rectangle) {
            str = "rectangle" + ";" + ((Rectangle) shape).getX() + ";" + ((Rectangle) shape).getY() + ";" + ((Rectangle) shape).getWidth() + ";" + ((Rectangle) shape).getHeight() + ";";
        }

        if (shape instanceof Point) {
            str = "point" + ";" + ((Point) shape).getX() + ";" + ((Point) shape).getY() + ";";
        }

        return str;
    }

    /**
     * Formate la liste de tags et l'envoie au parser pour les sauver sur
     * l'image ou les images de la sequence
     *
     * @param tags Liste des tags
     * @param imagesPath Chemin de l'image ou du dossier
     */
    public void saveTags(ArrayList<ArrayList<String>> tags, String imagesPath) {
        this.tags.clear();
        for (ArrayList<String> tag : tags) {
            setTag(tag, null);
        }

        TagHistory.saveTag(tags, imagesPath);

        parser.setTags(this.tags, imagesPath);
    }
}
