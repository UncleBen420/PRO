package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import JTreeManager.JTreeManager;

public class WeatherFilter extends TreeFilterGUI {
	
public WeatherFilter(final JTreeManager manager){
    	
    	super(manager);     
       
    }

protected void specialisation() {
	
	panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    JButton filter = new JButton("Filter");
    filter.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Filter");
        }
    });
    
    JLabel label = new JLabel("Weather");
    
    panel.add(label);
	
}
}
