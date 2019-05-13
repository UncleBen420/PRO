package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import JTreeManager.JTreeManager;
import searchfilters.AbstractTreeFilter;

/**
 * 
 * @author gaetan
 */
abstract public class TreeFilter extends JPanel {

    /**
     *
     */
    protected JPanel panel, specialistationPanel;
    protected JLabel label;
    private JButton delete;

    /**
     *
     */
    protected JButton filter;

    /**
     *
     */
    protected JTreeManager manager;

    /**
     *
     */
    protected AbstractTreeFilter currentFilter;

    /**
     *
     */
    protected TreeFilter() {

    }

    /**
     *
     * @param manager
     */
    public TreeFilter(final JTreeManager manager) {

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        specialistationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));    
        
        this.manager = manager;
        currentFilter = null;

        common();
        specialisation();

        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BorderLayout());
        panelButton.add(filter, BorderLayout.NORTH);
        panelButton.add(delete, BorderLayout.CENTER);
        
        panel.add(panelButton);
        panel.setPreferredSize(new Dimension(100, 60));
        this.setLayout(new BorderLayout());
        label.setFont(new Font(label.getText(), Font.BOLD, 12));
        this.add(label,BorderLayout.NORTH);
        this.add(specialistationPanel, BorderLayout.WEST);
        this.add(panel, BorderLayout.EAST);

    }

    /**
     *
     */
    protected abstract void specialisation();

    private void common() {

        filter = new JButton("Filter");

        delete = new JButton("Delete");

        delete.addActionListener((ActionEvent e) -> {
            if (currentFilter != null) {
                manager.removeFiltre(currentFilter);
                currentFilter = null;
            }            
        });

    }

}
