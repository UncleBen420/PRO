/**
 * PRO
 * Authors: Bacso
 * File: ChangeFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import JTreeManager.JTreeManager;

/**
 * Implémentation de l'interface pour le filtre par changement d'image
 *
 * @author gaetan
 */
public class ChangeFilter extends TreeFilter {

    private static final long serialVersionUID = -213970803241070452L;

    /**
     * Constrcuteur
     *
     * @param manager Jtree de la banque d'image
     */
    public ChangeFilter(final JTreeManager manager) {

        super(manager);

    }

    @Override
    protected void specialisation() {

        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Filter");
        });

        JLabel label = new JLabel("Change");

        panel.add(label);

    }

}
