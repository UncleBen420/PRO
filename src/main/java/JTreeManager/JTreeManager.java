package JTreeManager;

import GUI.GUIRender;
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
import Tag.TagHistory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import searchfilters.AbstractTreeFilter;
import jsontreeparse.JsonTreeParser;
import properties.PropertiesHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Groupe PRO B-9
 * Cette classe est une extention d'un jPanel
 * Elle englobe un jtree qu'elle gere et met a jour quand des filtre sur ce jTree on ete creer
 */
public class JTreeManager extends JPanel {

	private static final long serialVersionUID = 774488936584418358L;
        private GUIRender GUIRender = new GUIRender();
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

		// si un fichier json de l'arborescence existe, il va le prendre sinon il va en creer un
		if (this.JsonTree.exists()) {

			root = parser.setDirectoryTree(properties.getProperty("JsonBankPath"));
			showTree();
			flagFilter = true;

		} else {

			// averti l'utilisateur que le fichier n'a pas pu etre trouve, il demande aussi si il veut charger le fichier history
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
	 * @param f filtre etant ajouter au manager
	 */
	public void addFiltre(final AbstractTreeFilter f) {

		// si le jtree n'est pas encore sette, le filtre n'est pas ajoute
		if (!flagFilter) {
			return;
		}

		// on cree une thread qui se charge de filtrer le jtree
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

				}

				setText(f.toString() + " is deleted");
				tree.setEnabled(false);
				
				// pour eviter un phenomene de perte de noeud de l'arbre, on enleve tous les filtre apres celui devant etre enleve puis on les remets

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
	 * cette methode permet d'ajouter le texte qui va etre montre
	 * @param text qui va etre montre
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
                tree.setCellRenderer(new CellRenderer());
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
						
						Parser p = new Parser();
						ArrayList<ArrayList<String>> arraytemp = null;
						try {
							arraytemp = CsvParser.getTag(p.getTag((new File(value)).getAbsolutePath()));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						TagHistory.saveTag(arraytemp, (new File(value)).getAbsolutePath());

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
                sb.getViewport().getView().setBackground(GUIRender.getBackColor());
                sb.getVerticalScrollBar().setBackground(Color.DARK_GRAY);
		sb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(sb);

		messageBox = new JTextField();
                messageBox.setBackground(GUIRender.getBackColor());
                messageBox.setForeground(GUIRender.getForeColor());
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
        
        
        class CellRenderer extends DefaultTreeCellRenderer {

        private Font elementFont;
        private Font elementFontSelected;

        CellRenderer() {
            setElementFont();
        }

        @Override
        public Component getTreeCellRendererComponent(
                JTree tree, Object value, boolean isSelected, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {
            JComponent c = (JComponent) super.getTreeCellRendererComponent(
                    tree, value, isSelected, expanded, leaf, row, hasFocus);
            c.setOpaque(false);
            
            setClosedIcon( new javax.swing.ImageIcon(getClass().getResource("/images/icons8-folder-26.png")));
            setOpenIcon( new javax.swing.ImageIcon(getClass().getResource("/images/icons8-opened-folder-26.png")));
            setLeafIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-google-images-26.png")));
            
            setFont(elementFont);
            if (selected) {
                setFont(elementFontSelected);
            }

            return c;
        }
        private final Color ALPHA_OF_ZERO = new Color(0, true);

        @Override
        public Color getBackgroundNonSelectionColor() {
            return ALPHA_OF_ZERO;
        }

        @Override
        public Color getBackgroundSelectionColor() {
            return ALPHA_OF_ZERO;
        }

        @Override
        public Color getForeground() {
            return Color.WHITE;
        }

        private void setElementFont() {
            elementFont = GUIRender.GetElement();
            elementFontSelected = GUIRender.getElementSelected();

        }

    }
}
