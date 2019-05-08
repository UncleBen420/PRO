/**
 * PRO
 * Authors: Bacso
 * File: FiltersPanel.java
 * IDE: NetBeans IDE 11
 */
package GUI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import JTreeManager.JTreeManager;

/**
 * Classe implémentant l'interface pour les filtres
 * 
 * @author gaetan
 */
public class FiltersPanel extends JPanel {
	
	private JTreeManager manager;
        private final JPanel panel;
	
	public FiltersPanel() {
		manager = null;
		
		panel = new JPanel();
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JScrollPane scroll = new JScrollPane(panel);
		this.add(scroll);
		
	}
        
        public void setManager(JTreeManager manager){
            this.manager = manager;
            panel.add(new DateFiltrer(manager));
            panel.add(new ChangeFilter(manager));
            panel.add(new TagFilter(manager));
            panel.add(new WeatherFilter(manager));
        }

}
