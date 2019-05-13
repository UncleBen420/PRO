/**
 * PRO
 * Authors: Bacso
 * File: WeatherFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import JTreeManager.JTreeManager;
import meteoAPI.TYPEMETEO;
import searchfilters.MeteoTreeFilter;

/**
 * Classe implémentant l'interface pour le fitre meteo
 *
 * @author gaetan
 */
public class WeatherFilter extends TreeFilter {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4481484164630682831L;
	private boolean rainChecked = false;

    /**
     *
     * @param manager
     */
    public WeatherFilter(final JTreeManager manager) {

        super(manager);

    }

    /**
     *
     */
    protected void specialisation() {

        List<String> weathersString = new ArrayList<>();

        for (TYPEMETEO weather : TYPEMETEO.values()) {
            weathersString.add(weather.toString());
        }

        JComboBox weatherCombobox = new JComboBox(weathersString.toArray());
        JCheckBox rainCheckBox = new JCheckBox("rain");

        rainCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    rainChecked = true;
                } else {

                    rainChecked = false;
                };
            }
        });

        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentFilter != null) {
                    manager.removeFiltre(currentFilter);
                }

                currentFilter = new MeteoTreeFilter(TYPEMETEO.getTypeByString(String.valueOf(weatherCombobox.getSelectedItem())), rainChecked);
                manager.addFiltre(currentFilter);

            }
        });

        label = new JLabel("Weather");

        specialistationPanel.add(weatherCombobox);
        specialistationPanel.add(rainCheckBox);

    }
}
