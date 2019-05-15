/**
 * PRO
 * Authors: Bacso
 * File: WeatherFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
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

    private static final long serialVersionUID = -4481484164630682831L;
    //private boolean rainChecked = false;

    /**
     * Constructeur
     * 
     * @param manager jtree de la banque d'image
     */
    public WeatherFilter(final JTreeManager manager) {

        super(manager);

    }

    @Override
    protected void specialisation() {

        List<String> weathersString = new ArrayList<>();

        for (TYPEMETEO weather : TYPEMETEO.values()) {
            weathersString.add(weather.toString());
        }

        JComboBox<?> weatherCombobox;
        weatherCombobox = new JComboBox<>(weathersString.toArray());
       // JCheckBox rainCheckBox = new JCheckBox("rain");

        /*rainCheckBox.addItemListener((ItemEvent e) -> {
            rainChecked = ItemEvent.SELECTED == e.getStateChange();
        });*/

        filter.addActionListener((ActionEvent e) -> {
            if (currentFilter != null) {
                manager.removeFiltre(currentFilter);
            }
            
            currentFilter = new MeteoTreeFilter(TYPEMETEO.getTypeByString(String.valueOf(weatherCombobox.getSelectedItem())));
            manager.addFiltre(currentFilter);
        });

        label = new JLabel("Weather");

        specialistationPanel.add(weatherCombobox);
        //specialistationPanel.add(rainCheckBox);

    }
}
