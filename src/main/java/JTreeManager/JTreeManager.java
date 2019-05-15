package JTreeManager;


import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
 * @author gaetan
 */
public class JTreeManager extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 774488936584418358L;
	private final List<AbstractTreeFilter> Filtre;
	private final DefaultMutableTreeNode root;
	private JTree tree;
	private final File rootDirectory;
	private final File JsonTree;
	private SliderDemo slider;
	private ViewerTable table;
	private Parser parserTag = new Parser();
	private final Semaphore mutex = new Semaphore(1);
	private JTextField messageBox;

	private String value = "";

    /**
     *
     */
    public JTreeManager() {
        this.Filtre = new ArrayList<>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(new Dimension(500,500));
        this.setPreferredSize(new Dimension(100,100));
        
        
		Properties properties = PropertiesHandler.parseProperties();

		this.rootDirectory = new File(properties.getProperty("imageBankPath"));
		this.JsonTree = new File(properties.getProperty("JsonBankPath"));

		JsonTreeParser parser = new JsonTreeParser();

		if (this.JsonTree.exists()) {
			root = parser.setDirectoryTree(properties.getProperty("JsonBankPath"));

		} else {
			parser.createXML(rootDirectory);

			root = parser.setDirectoryTree((new File(properties.getProperty("JsonBankPath")).getAbsolutePath()));
		}

		tree = null;

		tree = new JTree(root);
		
		tree.setMinimumSize(new Dimension(100,100));

		tree.addMouseListener(new MouseAdapter() {

                        @Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					if (FilenameUtils.getExtension(((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).toString()).equals("jpg")) {

						value = rootDirectory.getAbsolutePath();

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

	}

    /**
     *
     * @param f
     */
    public void addFiltre(final AbstractTreeFilter f) {
    	
    	JPanel manager = this;

		Thread thread;
                thread = new Thread() {
                @Override
                public void run() {
                    
                    try {
                        mutex.acquire();
                    } catch (InterruptedException e) {
                        
                    }
                    
                    
                    messageBox.setEditable(true);
                    messageBox.setText(f.toString() + " is running.");
                    messageBox.setEditable(false);
                    
                    tree.setEnabled(false);
                    JLabel label = new JLabel();
                    manager.add(label);
                    Filtre.add(f);
                    f.setTree(tree);
                    f.filtreTree();
                    
                    tree.setEnabled(true);
                    
                    messageBox.setEditable(true);
                    messageBox.setText("");
                    messageBox.setEditable(false);
                    
                    mutex.release();
                    
                    
                }
            };

		thread.start();

	}

    /**
     *
     * @param f
     */
    public void removeFiltre(final AbstractTreeFilter f) {

		Thread thread = new Thread() {
                        @Override
			public void run() {

				try {
					mutex.acquire();
				} catch (InterruptedException e) {
                                    // TODO Auto-generated catch block

				}

				int i = Filtre.size();

				for (i--; i >= Filtre.indexOf(f); i--) {

					Filtre.get(i).unfiltreTree();

				}

				Filtre.remove(f);

				for (i++; i < Filtre.size(); i++) {

					Filtre.get(i).filtreTree();

				}
				
				mutex.release();

			}
		};

		thread.start();

	}

    /**
     *
     * @return
     */
    public String getValue() {
		return value;
	}

    /**
     *
     * @param s
     */
    public void setSlider(SliderDemo s) {
		slider = s;
	}

    /**
     *
     * @param t
     */
    public void setTable(ViewerTable t) {
		table = t;
	}
}
