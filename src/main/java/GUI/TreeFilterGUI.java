package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import JTreeManager.JTreeManager;
import searchfilters.treeFilter;

abstract public class TreeFilterGUI extends JPanel{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 7409356452760309804L;
		protected JPanel panel;
		private JButton delete;
		protected JButton filter;
		protected JTreeManager manager;
		protected treeFilter currentFilter;
	    
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
                	currentFilter = null;
                }

            }
        });
		
	}
	

}
