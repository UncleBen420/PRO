package Shapes;

public class Cercle extends Shapes{
	private double radius;
	
	Cercle(double x, double y, double r) {
		super(x, y);
		radius = r;
	}
	
	public double getRadius() { return radius; }

}
