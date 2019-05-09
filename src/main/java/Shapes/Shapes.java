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
 * @author gaetan
 */
public abstract class Shapes {
	private double x, y;
	
    /**
     *
     * @param x
     * @param y
     */
    public Shapes(double x, double y){
		this.x = x;
		this.y = y;
	}
	
    /**
     *
     * @return
     */
    public double getX() { return x; }

    /**
     *
     * @return
     */
    public double getY() { return y; }
}
