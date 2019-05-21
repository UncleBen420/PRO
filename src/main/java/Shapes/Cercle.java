/**
 * PRO
 * Authors: Bacso
 * File: Cercle.java
 * IDE: NetBeans IDE 11
 */
package Shapes;

/**
 * Classe implémentant les Cercle
 * 
 * @author Groupe PRO B-9
 */
public class Cercle extends Shapes{
	private double radius;
	
    /**
     * Constructeur
     * 
     * @param x position x
     * @param y position y
     * @param r rayon du cercle
     */
    public Cercle(double x, double y, double r) {
		super(x, y);
		radius = r;
	}
	
    /**
     * Accesseur du rayon
     * 
     * @return le rayon du cercle
     */
    public double getRadius() { return radius; }

}
