/**
 * PRO
 * Authors: Bacso
 * File: WeatherFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import JTreeManager.JTreeManager;
import searchfilters.TemperatureTreeFilter;

/**
 * Classe implémentant l'interface pour le fitre meteo
 *
 * @author gaetan
 */
public class TemperatureFilter extends TreeFilter {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8934247004768877673L;

    /**
     *
     * @param manager
     */
    public TemperatureFilter(final JTreeManager manager) {

        super(manager);

    }

    /**
     *
     */
    protected void specialisation() {
    	
    	JLabel label = new JLabel("Temperature");

    	final JTextField beginText = new JTextField("00.0");

        final JTextField endText = new JTextField("36.7");

        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (currentFilter != null) {
                    manager.removeFiltre(currentFilter);
                }
                currentFilter = null;

                
                try {
                currentFilter = new TemperatureTreeFilter(Double.parseDouble(beginText.getText()), Double.parseDouble(endText.getText()));
                manager.addFiltre(currentFilter);
                
                }catch(Exception ex) {
                	
                	JOptionPane.showMessageDialog(null,"The temperature entered cannot be parse");
                	
                }

            }
        });

        panel.add(label);
        panel.add(beginText);
        panel.add(endText);

    }
}
