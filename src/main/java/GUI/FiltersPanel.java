package GUI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import JTreeManager.JTreeManager;
import guitest.ChangeFilter;
import guitest.DateFiltreTest;
import guitest.TagFilterGUI;
import guitest.WeatherFilter;

public class FiltersPanel extends JPanel {
	
	private final JTreeManager manager;
	
	public FiltersPanel(JTreeManager manager) {
		this.manager = manager;
		
		JPanel panel = new JPanel();
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		panel.add(new DateFiltreTest(manager));
		panel.add(new ChangeFilter(manager));
		panel.add(new TagFilterGUI(manager));
		panel.add(new WeatherFilter(manager));
		
		JScrollPane scroll = new JScrollPane(panel);
		this.add(scroll);
		
	}

}
