package GUI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import JTreeManager.JTreeManager;

public class FiltersPanel extends JPanel {
	
	private JTreeManager manager;
        private JPanel panel;
	
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
            panel.add(new DateFiltreTest(manager));
            panel.add(new ChangeFilter(manager));
            panel.add(new TagFilterGUI(manager));
            panel.add(new WeatherFilter(manager));
        }

}
