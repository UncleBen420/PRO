/**
 * PRO
 * Authors: Bacso
 * File: ChangeFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
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
 * @author Groupe PRO B-9
 */
public class ChangeFilter extends TreeFilter {

    private static final long serialVersionUID = -213970803241070452L;

    /**
     * Constructeur
     *
     * @param manager Jtree de la banque d'image
     */
    public ChangeFilter(final JTreeManager manager) {

        super(manager);

    }

    @Override
    protected void specialisation() {
    	
        JPanel minMax = new JPanel();
        minMax.setOpaque(false);
        JTextField min = new JTextField("min");
       // min.setForeground(Color.WHITE);
        JLabel ratio = new JLabel("ratio accepte");
        ratio.setForeground(Color.WHITE);
       // min.setForeground(GUIRender.getForeColor());
        JTextField max = new JTextField("max");
       // max.setForeground(Color.WHITE);

        //max.setForeground(GUIRender.getForeColor());
        minMax.setLayout(new BoxLayout(minMax, BoxLayout.Y_AXIS));
        minMax.add(ratio);
    	minMax.add(min, BorderLayout.NORTH);
    	minMax.add(max, BorderLayout.CENTER);
    	
    	JPanel sliders = new JPanel();
        sliders.setOpaque(false);
        JSlider sliderPrecision = new JSlider(10,100);
        sliderPrecision.setOpaque(false);
        sliderPrecision.setPreferredSize(new Dimension(200,20));
        JSlider sliderTolerance = new JSlider(0,100);
        sliderTolerance.setOpaque(false);
        sliderTolerance.setPreferredSize(new Dimension(200,20));
        sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
        JLabel lprecision = new JLabel("precision");
        lprecision.setForeground (Color.WHITE);
        sliders.add(lprecision);
        sliders.add(sliderPrecision);
        JLabel ltolerance = new JLabel("tolerance");
        ltolerance.setForeground(Color.WHITE);
        sliders.add(ltolerance);
        sliders.add(sliderTolerance);
        
        
        
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
