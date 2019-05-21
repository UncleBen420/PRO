/**
 * PRO
 * Authors: Bacso
 * File: DateFilter.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTextField;
import JTreeManager.JTreeManager;
import java.awt.Color;
import java.awt.event.*;
import searchfilters.DateTreeFilter;

/**
 * Classe implémentant l'interface pour le filtre par date
 *
 * @author Groupe PRO B-9
 */
public class DateFiltrer extends TreeFilter {

    private static final long serialVersionUID = -8309810800818768681L;

    /**
     * Constructeur
     *
     * @param manager Jtree de la banque d'image
     */
    public DateFiltrer(final JTreeManager manager) {

        super(manager);

    }

    @Override
    protected void specialisation() {

        label = new JLabel("Date");

        final JTextField beginText = new JTextField("2017-03-23");
        beginText.setBackground(GUIRender.getButtonColor());
        beginText.setForeground(GUIRender.getForeColor());
        beginText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                beginText.setForeground(GUIRender.getForeColor());
            }

            @Override
            public void focusLost(FocusEvent fe) {
                beginText.setForeground(GUIRender.getForeColor());
            }
        });
        
        final JTextField endText = new JTextField("2017-03-23");
        endText.setBackground(GUIRender.getButtonColor());
        endText.setForeground(GUIRender.getForeColor());
        endText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                endText.setForeground(GUIRender.getForeColor());
            }

            @Override
            public void focusLost(FocusEvent fe) {
                endText.setForeground(GUIRender.getForeColor());
            }
        });

        filter.addActionListener((ActionEvent e) -> {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = null;
            Date endDate = null;

            try {
                beginDate = df.parse(beginText.getText());
            } catch (ParseException e2) {

            }
            try {
                endDate = df.parse(endText.getText());
            } catch (ParseException e1) {

            }

            if (currentFilter != null) {
                manager.removeFiltre(currentFilter);
            }

            currentFilter = new DateTreeFilter(beginDate, endDate);
            manager.addFiltre(currentFilter);
        });

        specialistationPanel.add(beginText);
        specialistationPanel.add(endText);

    }
   

}
