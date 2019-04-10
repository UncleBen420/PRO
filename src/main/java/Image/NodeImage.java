package Image;

import javax.swing.tree.DefaultMutableTreeNode;

import interfaces.Filtrable;
import interfaces.Visitor;
import searchfilters.SearchFilter;

public class NodeImage extends DefaultMutableTreeNode implements Filtrable {
	
	private SearchFilter filtred;
	
	public NodeImage(String s) {
		super(s);
	}
	
	public NodeImage() {
		super();
	}

	public boolean isFiltred() {
		if(filtred != null) {
			return true;
		}
		return false;
	}

	public void Filtre(SearchFilter filter) {
		if(!isFiltred()) {
			Image temp = new Image(this.toString());
			filter.visit(temp);
			if(temp.isFiltred()) {
				filtred = filter;
			}
			
		}
		
	}

	public void unFiltre(SearchFilter filter) {
		if (filter.equals(getFilter())) {
			filtred = null;			
		}
	}

	public SearchFilter getFilter() {
		return filtred;
	}

}
