
public class Rectangle extends Forme{
	private double largeur;
	private double hauteur;

	Rectangle(double x, double y, double h, double l) {
		super(x, y);
		hauteur = h;
		largeur = l;
	}
	
	public double getLargeur() { return largeur; }
	public double getHauteur() { return hauteur; }
	
}
