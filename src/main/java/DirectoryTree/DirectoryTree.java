package DirectoryTree;

import Image.NodeImage;
import interfaces.Filtrable;
import interfaces.Visitor;
import searchfilters.SearchFilter;
import interfaces.Explorable;
import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Comparator;
import org.apache.commons.io.FilenameUtils;
import javax.swing.tree.DefaultMutableTreeNode;

public class DirectoryTree extends DefaultMutableTreeNode implements Filtrable, Explorable {

	private List<DirectoryTree> childrenFiltred = new LinkedList<DirectoryTree>();
	private DirectoryTree oldParent;
	private SearchFilter isFiltred;

	public DirectoryTree() {
		super("root");

		isFiltred = null;
	}

	public DirectoryTree(String name) {
		super(name);
		isFiltred = null;
	}

	public boolean isFiltred() {
		if (isFiltred != null) {
			return true;
		}
		return false;
	}

	public void explore(Visitor visitor) {

		visitor.visit(this);
		this.explore(this, visitor);
	}

	public void explore(DirectoryTree e, Visitor visitor) {

		int j;
		if (e.children != null)
			for (int i = 0; i < (j = e.children.size()); i++){	
				
				Object temp = e.children.elementAt(i);
				if(temp instanceof Filtrable) {
					
						visitor.visit((Filtrable)temp);
						if (((Filtrable)temp).isFiltred()) {
							i--;
							j--;
						}else if (!((Filtrable)temp).isFiltred() && temp instanceof DirectoryTree) {
							explore(((DirectoryTree)temp),visitor);
						}
						

				}
				
			}
	}
	


	public void exploreFiltredChildren(Visitor visitor) {
		
		final Visitor temp = visitor;

		explore(new Visitor(){

			public void visit(Filtrable f) {
				
				if(f instanceof DirectoryTree && ((DirectoryTree)f).childrenFiltred != null)
				for(Filtrable t : ((DirectoryTree)f).childrenFiltred) {

					
					temp.visit(t);
					
					if(t instanceof DirectoryTree) {
						
						explore(((DirectoryTree)t),temp);
						
					}
					
				}
			}
	
		});
	}

	public void setDirectoryTree(File rootDirectory) {

		if (rootDirectory.isDirectory()) {

			File[] childrenDir = rootDirectory.listFiles();
			Arrays.sort(childrenDir);

			for (File child : childrenDir) {

				if (child.isDirectory()) {
					DirectoryTree temp = new DirectoryTree(child.getName());
					this.add(temp);
					temp.setDirectoryTree(child);

				} else {
					
					if(FilenameUtils.getExtension(child.getName()).equals("jpg")){
						NodeImage temp = new NodeImage(child.getName());
						this.add(temp);
					}
				}

			}
		}

	}

	public void Filtre(SearchFilter filter) {
		isFiltred = filter;

		if(getParent() != null) {
	
			
			DirectoryTree parent = (DirectoryTree)getParent();
			parent.remove(this);
			parent.childrenFiltred.add(this);
			oldParent = parent;

		}
		
	}
	


	public void unFiltre(SearchFilter filter) {

		final SearchFilter temp = filter;

		exploreFiltredChildren(new Visitor() {

			public void visit(Filtrable f) {

				if (temp.equals(f.getFilter())) {
					((DirectoryTree)f).isFiltred = null;
				}
				
				if(!f.isFiltred() && f instanceof DirectoryTree && ((DirectoryTree)f).oldParent != null) {
					((DirectoryTree)f).oldParent.childrenFiltred.remove((DirectoryTree)f);
					((DirectoryTree)f).oldParent.add((DirectoryTree)f);
					((DirectoryTree)f).oldParent = null;	
				}

			}

		});
		
		sortTree();
	}
	
	
public void sortTree() {
		
		explore(new Visitor(){

			@SuppressWarnings("unchecked")
			public void visit(Filtrable f) {
				
				if(f instanceof DirectoryTree && ((DirectoryTree)f).children != null) {
					((DirectoryTree)f).children.sort(new Comparator() {
						public int compare(Object o1, Object o2) {
							return o1.toString().compareTo(o2.toString());
						}	
					});
				}
					
			}
		});
	}



	public SearchFilter getFilter() {
		return isFiltred;
	}

}
