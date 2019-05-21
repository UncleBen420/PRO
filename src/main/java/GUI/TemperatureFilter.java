/**
 * PRO
 * Authors: Bacso
 * File: WeatherFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import JTreeManager.JTreeManager;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import searchfilters.TemperatureTreeFilter;

/**
 * Classe implémentant l'interface pour le fitre meteo
 *
 * @author Groupe PRO B-9
 */
public class TemperatureFilter extends TreeFilter {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8934247004768877673L;

    /**
     * Constructeur
     * @param manager JTreeManager sur lequelle les filtres vont filtrer
     */
    public TemperatureFilter(final JTreeManager manager) {

        super(manager);

    }

    /**
     * specialisation des filtres pour la temp�rature
     */
        @Override
    protected void specialisation() {
    	
    	label = new JLabel("Temperature");

    	final JTextField beginText = new JTextField("00.0");
        beginText.setBackground(GUIRender.getButtonColor());
        beginText.setForeground(GUIRender.getForeColor());
        beginText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                beginText.setForeground(Color.WHITE);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                beginText.setForeground(Color.WHITE);
            }
        });
        final JTextField endText = new JTextField("36.7");
        endText.setBackground(GUIRender.getButtonColor());
        endText.setForeground(GUIRender.getForeColor());
        endText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                endText.setForeground(Color.WHITE);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                endText.setForeground(Color.WHITE);
            }
        });

        filter.addActionListener((ActionEvent e) -> {
            if (currentFilter != null) {
                manager.removeFiltre(currentFilter);
            }
            currentFilter = null;
            
            
            try {
                currentFilter = new TemperatureTreeFilter(Double.parseDouble(beginText.getText()), Double.parseDouble(endText.getText()));
                manager.addFiltre(currentFilter);
                
            }catch(NumberFormatException ex) {
                
                JOptionPane.showMessageDialog(null,"The temperature entered cannot be parse");
                
            }
            });

        specialistationPanel.add(beginText);
        specialistationPanel.add(endText);

    }
}
