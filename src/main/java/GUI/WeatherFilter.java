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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import JTreeManager.JTreeManager;

/**
 * Classe implémentant l'interface pour le fitre meteo
 *
 * @author gaetan
 */
public class WeatherFilter extends TreeFilter {

    public WeatherFilter(final JTreeManager manager) {

        super(manager);

    }

    @Override
    protected void specialisation() {

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Filter");
        });

        JLabel label = new JLabel("Weather");

        panel.add(label);

    }
}
