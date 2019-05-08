/**
 * PRO
 * Authors: Bacso
 * File: WeatherFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import JTreeManager.JTreeManager;
import meteoAPI.TYPEMETEO;
import searchfilters.MeteoTreeFilter;
import searchfilters.TreeFilterDate;

/**
 * Classe implémentant l'interface pour le fitre meteo
 *
 * @author gaetan
 */
public class WeatherFilter extends TreeFilter {

    private boolean rainChecked = false;

    public WeatherFilter(final JTreeManager manager) {

        super(manager);

    }

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

        JLabel label = new JLabel("Weather");

        panel.add(label);
        panel.add(weatherCombobox);
        panel.add(rainCheckBox);

    }
}
