package Shapes;

public abstract class Shapes {
	private double x, y;
	
	Shapes(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
}
