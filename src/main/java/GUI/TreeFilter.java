package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import JTreeManager.JTreeManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import searchfilters.AbstractTreeFilter;

/**
 * Cette classe est une représentation graphique des filtres sur le JTree
 * @author Groupe PRO B-9
 */
abstract public class TreeFilter extends JPanel {

    /**
     *
     */
    protected JPanel panel, specialistationPanel;
    protected JLabel label;

    private static final long serialVersionUID = 8378656726744781478L;
    private final GUIRender GUIRender = new GUIRender();

    private JButton delete;
    protected JButton filter;
    protected JTreeManager manager;
    protected AbstractTreeFilter currentFilter;

    /**
     * Constructeur
     */
    protected TreeFilter() {

    }

    /**
     * Constructeur avec manager
     *
     * @param manager jtree de la banque d'image
     */
    public TreeFilter(final JTreeManager manager) {



        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));


        specialistationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        specialistationPanel.setBackground(GUIRender.getBackColor());

        this.manager = manager;
        currentFilter = null;

        common();
        specialisation();

        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BorderLayout());
        panelButton.add(filter, BorderLayout.NORTH);
        panelButton.add(delete, BorderLayout.CENTER);

        panel.setBackground(GUIRender.getBackColor());
        panel.add(panelButton);
        panel.setPreferredSize(new Dimension(100, 80));

        label.setForeground(GUIRender.getForeColor());
        label.setFont(GUIRender.getSectionTitle());

        this.setBackground(GUIRender.getBackColor());
        this.setLayout(new BorderLayout());
        this.add(label,BorderLayout.NORTH);
        this.add(specialistationPanel, BorderLayout.WEST);
        this.add(panel, BorderLayout.EAST);

    }

    /**
     * Cet methode doit etre implémentée dans les sous classe extendant celle-ci
     * Elle permet d'ajouter des éléments graphiques différents entre les filtres
     */
    protected abstract void specialisation();

    /**
     * Methode qui crée les éléments graphiques de base commun à chaque filtre
     */
    private void common() {

        filter = new SelButton("Filter");
        filter.setForeground(GUIRender.getForeColor());

        delete = new SelButton("Delete");
        delete.setForeground(GUIRender.getForeColor());

        delete.addActionListener((ActionEvent e) -> {
            if (currentFilter != null) {
                manager.removeFiltre(currentFilter);
                currentFilter = null;
            }
        });

    }

    private class SelButton extends JButton {

        public SelButton(String text) {
            super(text);
            UIManager.put("Button.foreground", new ColorUIResource(GUIRender.getForeColor()));
            UIManager.put("Button.mouseHoverColor", new ColorUIResource(GUIRender.getButtonSelectedColor()));
            UIManager.put("Button.background", new ColorUIResource(GUIRender.getButtonColor()));
            SwingUtilities.updateComponentTreeUI(this);

        }

    }

}
