package Tag;

import java.util.ArrayList;

import Shapes.*;

public class Tag {
	private ArrayList<String> tags = new ArrayList<String>();
	
	public void setTag(ArrayList<String> tag, Shapes shape) {
		if(shape == null) {
			tags.add(formatTag(tag));
		} else {
			tags.add(formatTag(tag) + formatShape(shape));
		}
		
	}
	
	private String formatTag(ArrayList<String> tag) {
		String str = "";
		
		for(String s : tag) {
			str += s + ";";
		}
		
		return str;
	}
	
	private String formatShape(Shapes shape) {
		String str = "";
		
		if(shape instanceof Cercle) {
			str = ((Cercle)shape).getX() + ((Cercle)shape).getY() + ((Cercle)shape).getRadius() + ";";
		}
		
		if(shape instanceof Rectangle) {
			str = ((Rectangle)shape).getX() + ((Rectangle)shape).getY() + ((Rectangle)shape).getWidth() + ((Rectangle)shape).getHeight() + ";";
		}
		
		if(shape instanceof Point) {
			str = ((Point)shape).getX() + ((Point)shape).getY() + ";";
		}
		
		return str;
	}
	
	public void saveTags() {
		
	}
}
