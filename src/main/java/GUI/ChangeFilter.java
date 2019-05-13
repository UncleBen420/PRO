/**
 * PRO
 * Authors: Bacso
 * File: ChangeFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import JTreeManager.JTreeManager;
import searchfilters.DateTreeFilter;
import searchfilters.RatioTreeFilter;

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
    	
    	JPanel minMax = new JPanel();
    	JTextField min = new JTextField("min");
        JTextField max = new JTextField("max");
    	minMax.setLayout(new BorderLayout());
    	minMax.add(min, BorderLayout.NORTH);
    	minMax.add(max, BorderLayout.CENTER);
    	
    	JPanel sliders = new JPanel();
        JSlider sliderPrecision = new JSlider(10,100);
        JSlider sliderTolerance = new JSlider(0,100);
        sliders.setLayout(new BorderLayout());
        sliders.add(sliderPrecision, BorderLayout.NORTH);
        sliders.add(sliderTolerance, BorderLayout.CENTER);
        
        
        
        label = new JLabel("Change");
        
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                

                if (currentFilter != null) {
                    manager.removeFiltre(currentFilter);
                }

                currentFilter = new RatioTreeFilter(Integer.parseInt(min.getText()), Integer.parseInt(max.getText()),sliderTolerance.getValue(),sliderPrecision.getValue());
                manager.addFiltre(currentFilter);

            }
        });

        specialistationPanel.add(minMax);
        specialistationPanel.add(sliders);
    }

}
