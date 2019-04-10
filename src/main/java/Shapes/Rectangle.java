package Shapes;

public class Rectangle extends Shapes{
	private double width;
	private double height;

	Rectangle(double x, double y, double h, double l) {
		super(x, y);
		width = h;
		height = l;
	}
	
	public double getWidth() { return width; }
	public double getHeight() { return height; }
	
}
