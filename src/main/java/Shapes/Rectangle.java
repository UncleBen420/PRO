package Shapes;

/**
 * Classe implementant les rectangles
 * 
 * @author Groupe PRO B-9
 */
public class Rectangle extends Shapes{
	private double width;
	private double height;

    /**
     * Constructeur 
     * 
     * @param x position x
     * @param y position y
     * @param h hauteur
     * @param l largeur
     */
    public Rectangle(double x, double y, double h, double l) {
		super(x, y);
		width = l;
		height = h;
	}
	
    /**
     * Accesseur de la largeur
     * 
     * @return la largeur du rectangle
     */
    public double getWidth() { return width; }

    /**
     * Accesseur de la hauteur
     * 
     * @return la hauteur du rectangle
     */
    public double getHeight() { return height; }
	
}
