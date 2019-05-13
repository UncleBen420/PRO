package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import JTreeManager.JTreeManager;
import searchfilters.treeFilter;

/**
 *
 * @author gaetan
 */
abstract public class TreeFilter extends JPanel {

    private static final long serialVersionUID = 8378656726744781478L;
    protected JPanel panel;
    private JButton delete;
    protected JButton filter;
    protected JTreeManager manager;
    protected treeFilter currentFilter;

    /**
     * Constructeur
     */
    protected TreeFilter() {

    }

    /**
     * Constructeur avec manager
     * 
     * @param manager jtree de la banque d'image
     */
    public TreeFilter(final JTreeManager manager) {

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        this.manager = manager;
        currentFilter = null;

        common();
        specialisation();

        panel.add(filter);
        panel.add(delete);

        this.add(panel);

    }

    protected abstract void specialisation();

    private void common() {

        filter = new JButton("Filter");

        delete = new JButton("Delete");

        delete.addActionListener((ActionEvent e) -> {
            if (currentFilter != null) {
                manager.removeFiltre(currentFilter);
            }
        });

    }

}
