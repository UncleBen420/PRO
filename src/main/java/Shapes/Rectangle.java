/**
 * PRO
 * Authors: Bacso
 * File: Rectangle.java
 * IDE: NetBeans IDE 11
 */
package Shapes;

/**
 * Classe implémentant les rectangles
 * 
 * @author gaetan
 */
public class Rectangle extends Shapes{
	private double width;
	private double height;

	public Rectangle(double x, double y, double h, double l) {
		super(x, y);
		width = h;
		height = l;
	}
	
	public double getWidth() { return width; }
	public double getHeight() { return height; }
	
}
