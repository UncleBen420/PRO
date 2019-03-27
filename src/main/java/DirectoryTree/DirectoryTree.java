package DirectoryTree;

import Image.Image;

import java.util.List;
import java.util.LinkedList;

public class DirectoryTree {
	
	private List<DirectoryTree> children = new LinkedList<DirectoryTree>();
	private List<Image> images = new LinkedList<Image>();
	private boolean isFiltred;
	private String name;
	
	public DirectoryTree() {
		name = "root";
		isFiltred = false;
	}
	
	public DirectoryTree(String name) {
		this.name = name;
		isFiltred = false;
	}
	
	public void addChild(String directoryName) {
		children.add(new DirectoryTree(directoryName));
	}
	
	public void addImage(Image image) {
		images.add(image);
	}
	
	public void visit() {
		
	}
	

}
