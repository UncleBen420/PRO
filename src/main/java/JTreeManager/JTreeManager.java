package JTreeManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import org.apache.commons.io.FilenameUtils;
import GUI.SliderDemo;
import GUI.ViewerTable;
import Tag.CsvParser;
import Tag.Parser;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import searchfilters.AbstractTreeFilter;
import jsontreeparse.JsonTreeParser;
import properties.PropertiesHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Groupe PRO B-9
 * Cette classe est une extention d'un jPanel
 * Elle englobe un jtree qu'elle g�re et met a jour quand des filtre sur ce jTree on �t� cr�er
 */
public class JTreeManager extends JPanel {

	private static final long serialVersionUID = 774488936584418358L;
	private final List<AbstractTreeFilter> Filtre;
	private DefaultMutableTreeNode root;
	private JTree tree;
	private final File rootDirectory;
	private final File JsonTree;
	private SliderDemo slider;
	private ViewerTable table;
	private Parser parserTag = new Parser();
	private final Semaphore mutex = new Semaphore(1);
	private JTextField messageBox;
	private Boolean flagFilter = false;
	JPanel waitingPanel = null;

	/**
	 * Constructeur du JtreeManager
	 */
	public JTreeManager() {
		
		this.Filtre = new ArrayList<>();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(new Dimension(500, 500));
		this.setPreferredSize(new Dimension(100, 100));

		// charge les emplacement des dossiers des image et du fichier json des images
		Properties properties = PropertiesHandler.parseProperties();
		this.rootDirectory = new File(properties.getProperty("imageBankPath"));
		this.JsonTree = new File(properties.getProperty("JsonBankPath"));

		JsonTreeParser parser = new JsonTreeParser();

		// si un fichier json de l'arborescence existe, il va le prendre sinon il va en cr�er un
		if (this.JsonTree.exists()) {

			root = parser.setDirectoryTree(properties.getProperty("JsonBankPath"));
			showTree();
			flagFilter = true;

		} else {

			// averti l'utilisateur que le fichier n'a pas pu etre trouv�, il demande aussi si il veut charger le fichier history
			int reply = JOptionPane.showConfirmDialog(null, "The program cannot with the json Directory tree file,\nIt start parsing a new one.\nWould you like to parse a new history.json file ?", "CrapauducViewer can't find json file", JOptionPane.YES_NO_OPTION);

			showWaiting();
			
			// cree une nouvelle thread qui se charge de parser les dossiers
			JTreeManager manager = this;
			Thread thread;
			thread = new Thread() {
				@Override
				public void run() {

					parser.createJson(rootDirectory,reply);
					root = parser
							.setDirectoryTree((new File(properties.getProperty("JsonBankPath")).getAbsolutePath()));
					manager.showTree();
					flagFilter = true;

				}
			};

			thread.start();

		}
	}

	/**
	 *
	 * @param f filtre �tant ajouter au manager
	 */
	public void addFiltre(final AbstractTreeFilter f) {

		// si le jtree n'est pas encore sett�, le filtre n'est pas ajout�
		if (!flagFilter) {
			return;
		}

		// on cr�e une thread qui se charge de filtrer le jtree
		Thread thread;
		thread = new Thread() {
			@Override
			public void run() {

				try {
					mutex.acquire();
				} catch (InterruptedException e) {

				}

				setText(f.toString() + " is running.");

				tree.setEnabled(false);

				Filtre.add(f);
				f.setTree(tree);
				f.filtreTree();

				tree.setEnabled(true);

				releaseText();

				mutex.release();

			}
		};

		thread.start();

	}

	/**
	 *
	 * @param f le filtre devant etre enlever
	 */
	public void removeFiltre(final AbstractTreeFilter f) {

		if (!flagFilter) {
			return;
		}

		Thread thread = new Thread() {
			@Override
			public void run() {

				try {
					mutex.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block

				}

				setText(f.toString() + " is deleted");
				tree.setEnabled(false);
				
				// pour eviter un phenom�ne de perte de noeud de l'arbre, on enl�ve tous les filtre apr�s celui devant etre enlev� puis on les remets

				int i = Filtre.size();
				for (i--; i >= Filtre.indexOf(f); i--) {

					Filtre.get(i).unfiltreTree();

				}

				Filtre.remove(f);

				for (i++; i < Filtre.size(); i++) {

					Filtre.get(i).filtreTree();

				}

				tree.setEnabled(true);
				releaseText();

				mutex.release();

			}
		};

		thread.start();

	}
	
	/**
	 *
	 * @param s slider qui va etre ajouter
	 */
	public void setSlider(SliderDemo s) {
		slider = s;
	}

	/**
	 *
	 * @param t qui va etre ajouter
	 */
	public void setTable(ViewerTable t) {
		table = t;
	}

	/**
	 * le JTreeManager contient un text qui permet d'afficher des informations
	 * cette methode permet d'ajouter le texte qui va etre montr� 
	 * @param text qui va etre montr�
	 */
	private void setText(String text) {

		messageBox.setEditable(true);
		messageBox.setText(text);
		messageBox.setEditable(false);

	}

	
	/**
	 * Permet de clear le label
	 */
	private void releaseText() {

		messageBox.setEditable(true);
		messageBox.setText("");
		messageBox.setEditable(false);

	}

	/**
	 * affiche le tree
	 */
	private void showTree() {

		if (waitingPanel != null) {

			this.remove(waitingPanel);
			waitingPanel = null;

		}

		tree = new JTree(root);

		tree.setMinimumSize(new Dimension(100, 100));

		tree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2 && tree.isEnabled()) {

					if (FilenameUtils
							.getExtension(((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).toString())
							.equals("jpg")) {

						String value = rootDirectory.getAbsolutePath();

						TreeNode[] elements = ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getPath();
						for (int i = 1, n = elements.length; i < n; i++) {
							value += "/" + elements[i];

						}
						slider.addImage(value);

						try {
							table.setTags(CsvParser.getTag(parserTag.getTag(value)));
						} catch (IOException ex) {
							Logger.getLogger(JTreeManager.class.getName()).log(Level.SEVERE, null, ex);
						}

					}
				}
			}

		});

		JScrollPane sb = new JScrollPane();
		sb.setViewportView(tree);
		sb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(sb);

		messageBox = new JTextField();
		messageBox.setEditable(false);
		this.add(messageBox);
		this.repaint();
	}

	/**
	 * affiche une bar de progression d'attente
	 */
	private void showWaiting() {

		waitingPanel = new JPanel();

		waitingPanel.setLayout(new BoxLayout(waitingPanel, BoxLayout.Y_AXIS));

		messageBox = new JTextField();

		messageBox.setEditable(false);
		JProgressBar pb = new JProgressBar();
		pb.setIndeterminate(true);
		
		waitingPanel.add(messageBox, BorderLayout.SOUTH);
		waitingPanel.add(pb, BorderLayout.SOUTH);

		this.add(waitingPanel, BorderLayout.SOUTH);

	}
}
