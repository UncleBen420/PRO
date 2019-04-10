package JTreeManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import DirectoryTree.DirectoryTree;
import Image.NodeImage;
import searchfilters.SearchFilter;

@SuppressWarnings("serial")
public class JTreeManager extends JPanel{

	private List<SearchFilter> Filtre = new ArrayList<SearchFilter>();

	private DirectoryTree root;
	private JTree tree;
    private File rootDirectory;

	private String value = "";

	public JTreeManager(File rootDirectory) {
		root = new DirectoryTree();
		root.setDirectoryTree(rootDirectory);
		tree = null;
		this.rootDirectory = rootDirectory;

	}

	public void addFiltre(SearchFilter f) {
		Filtre.add(f);
		f.setFiltred(root);
		root.explore(f);
		updateJtree();
	}

	public void removeFiltre(SearchFilter f) {
		Filtre.remove(f);
		f.cleanFilter();
		updateJtree();
	}

	public void updateJtree() {
		if (tree != null)
			this.remove(tree);
		tree = new JTree(root);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				TreePath treepath = e.getPath();

				value = rootDirectory.getAbsolutePath();
				if(treepath.getLastPathComponent() instanceof NodeImage) {
				Object elements[] = treepath.getPath();
				
				for (int i = 1, n = elements.length; i < n; i++) {
					value +="\\" +  elements[i];

				}

				System.out.println(value);
				}
			}
		});

		this.add(tree);

	}
}
