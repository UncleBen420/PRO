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
 * @author gaetan
 */
public class Cercle extends Shapes{
	private double radius;
	
    /**
     *
     * @param x
     * @param y
     * @param r
     */
    public Cercle(double x, double y, double r) {
		super(x, y);
		radius = r;
	}
	
    /**
     *
     * @return
     */
    public double getRadius() { return radius; }

}
