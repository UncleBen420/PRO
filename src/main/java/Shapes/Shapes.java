/**
 * PRO
 * Authors: Bacso
 * File: Shapes.java
 * IDE: NetBeans IDE 11
 */
package Shapes;

/**
 * Classe implémentant les formes
 * 
 * @author Groupe PRO B-9
 */
public abstract class Shapes {
	private double x, y;
	
    /**
     * Constructeur d'une forme
     * 
     * @param x position en x
     * @param y position en y
     */
    public Shapes(double x, double y){
		this.x = x;
		this.y = y;
	}
	
    /**
     * Accesseur de x
     * 
     * @return la position x de la forme
     */
    public double getX() { return x; }

    /**
     * Accesseur de y
     * 
     * @return la position y de la forme
     */
    public double getY() { return y; }
}
