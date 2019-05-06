package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import JTreeManager.JTreeManager;
public class ChangeFilter extends TreeFilterGUI {
	
/**
	 * 
	 */
	private static final long serialVersionUID = -213970803241070452L;

public ChangeFilter(final JTreeManager manager){
    	
    	super(manager);     
       
    }

protected void specialisation() {
	
	JButton filter = new JButton("Filter");
    filter.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Filter");
        }
    });
    
    JLabel label = new JLabel("Change");
    
    panel.add(label);
	
}

}
