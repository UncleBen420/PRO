/**
 * PRO
 * Authors: Bacso
 * File: TagFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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


        JCheckBox taggedCheckBox = new JCheckBox("tagged");

        taggedCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	tagChecked = true;
                } else {

                	tagChecked = false;
                };
            }
        });

        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentFilter != null) {
                    manager.removeFiltre(currentFilter);
                }

                currentFilter = new TagTreeFilter(tagChecked);
                manager.addFiltre(currentFilter);

            }
        });

        JLabel label = new JLabel("Weather");

        panel.add(label);
        panel.add(taggedCheckBox);


    }

}
