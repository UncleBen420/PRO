/**
 * PRO
 * Authors: Bacso
 * File: TagFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import JTreeManager.JTreeManager;

/**
 * Classe implémentant l'interface pour le filtre par tag
 *
 * @author gaetan
 */
public class TagFilter extends TreeFilter {

    /**
     *
     * @param manager
     */
    public TagFilter(final JTreeManager manager) {

        super(manager);

    }

    /**
     *
     */
    @Override
    protected void specialisation() {

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Filter");
        });

        JLabel label = new JLabel("Tag");

        panel.add(label);

    }

}
