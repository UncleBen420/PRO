/**
 * PRO
 * Authors: Bacso
 * File: TagFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import JTreeManager.JTreeManager;
import searchfilters.TagTreeFilter;

/**
 * Classe implémentant l'interface pour le filtre par tag
 *
 * @author gaetan
 */
public class TagFilter extends TreeFilter {

    /**
     *
     */
    private static final long serialVersionUID = -5442521176850560618L;
    private boolean tagChecked = false;

    /**
     * Constructeur
     *
     * @param manager jtree de la banque d'image
     */
    public TagFilter(final JTreeManager manager) {

        super(manager);

    }

    @Override
    protected void specialisation() {

        JCheckBox taggedCheckBox = new JCheckBox("tagged");

        taggedCheckBox.addItemListener((ItemEvent e) -> {
            tagChecked = e.getStateChange() == ItemEvent.SELECTED;
        });

        filter.addActionListener((ActionEvent e) -> {
            if (currentFilter != null) {
                manager.removeFiltre(currentFilter);
            }
            
            currentFilter = new TagTreeFilter(tagChecked);
            manager.addFiltre(currentFilter);
        });

        JLabel label = new JLabel("Weather");

        panel.add(label);
        panel.add(taggedCheckBox);

    }

}
