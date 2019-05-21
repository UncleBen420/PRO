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
import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import meteoAPI.TYPEMETEO;
import searchfilters.MeteoTreeFilter;

/**
 * Classe implémentant l'interface pour le fitre meteo
 *
 * @author Groupe PRO B-9
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
        weatherCombobox.setRenderer(new ComboBoxRenderer());
        weatherCombobox.setBackground(GUIRender.getButtonColor());
        weatherCombobox.setForeground(GUIRender.getForeColor());
        //((JTextField) weatherCombobox.getEditor().getEditorComponent()).setBackground(Color.YELLOW);
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
    
    
    private class ComboBoxRenderer extends JLabel implements ListCellRenderer {

        //private Color selectionBackgroundColor;
        //private DefaultListCellRenderer dlcr = new DefaultListCellRenderer();

        // Constructor
        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
            //selectionBackgroundColor = this.dlcr.getBackground(); // Have to set a color, else a compiler error will occur
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setBackground(list.getBackground());
            setForeground(GUIRender.getForeColor());
            setText((String) value);
            setFont(GUIRender.GetElement());
            // Check which item is selected
            if (isSelected) {
                // Set background color of the item your cursor is hovering over to the original background color
                setBackground(GUIRender.getButtonSelectedColor());
            } else {
                setBackground(GUIRender.getButtonColor());

            }
            String selectedText = getText();
            list.setSelectionBackground(GUIRender.getButtonColor());
            list.setSelectionForeground(GUIRender.getForeColor());
            return this;
        }
    }

}
