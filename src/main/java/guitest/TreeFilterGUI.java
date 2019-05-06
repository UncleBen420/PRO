package guitest;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import JTreeManager.JTreeManager;
import searchfilters.TreeFilterDate;

abstract public class TreeFilterGUI extends JPanel{
	
		protected JPanel panel;
		private JButton delete;
		protected JButton filter;
		protected JTreeManager manager;
		protected TreeFilterDate currentFilter;
	    
	protected TreeFilterGUI() {
		
	}
		
	public TreeFilterGUI(final JTreeManager manager){
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    	
	    	this.manager = manager;
	    	currentFilter = null;
	    	
	    	common();
	    	specialisation();
	    	
	    	panel.add(filter);
	        panel.add(delete);

	    	this.add(panel);      
	       
	}
	
	protected abstract void specialisation();
	
	private void common() {
	
		
		filter = new JButton("Filter");
        
        delete = new JButton("Delete");
        
        delete.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	if(currentFilter != null) {
                	manager.removeFiltre(currentFilter);
                }

            }
        });
		
	}
	

}
