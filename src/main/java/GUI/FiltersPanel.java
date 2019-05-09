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

	/**
	* 
	*/
	private static final long serialVersionUID = -7086436031326674714L;
	private final JPanel panel;

	/**
	 *
	 */
	public FiltersPanel() {

		panel = new JPanel();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JScrollPane scroll = new JScrollPane(panel);
		this.add(scroll);

	}

	/**
	 *
	 * @param manager
	 */
	public void setManager(JTreeManager manager) {
		panel.add(new DateFiltrer(manager));
		panel.add(new ChangeFilter(manager));
		panel.add(new TagFilter(manager));
		panel.add(new WeatherFilter(manager));
		panel.add(new TemperatureFilter(manager));
	}

}
