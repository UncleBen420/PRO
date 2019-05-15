package errormessage;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author internet_2
 */
public class ErrorMessage {

	private JFrame dialog;
	private JProgressBar progressBar;

    public void doJob(int max, String message) {

        JTextArea msgLabel;
        JPanel panel;

        progressBar = new JProgressBar(0, max);
        progressBar.setIndeterminate(false);
        
        msgLabel = new JTextArea(message);
        msgLabel.setEditable(false);
        panel = new JPanel(new BorderLayout(5, 5));
        panel.add(msgLabel, BorderLayout.PAGE_START);
        panel.add(progressBar, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(11, 11, 11, 11));

        dialog = new JFrame();
        dialog.getContentPane().add(panel);
        dialog.setResizable(false);
        dialog.pack();
        dialog.setSize(500, dialog.getHeight());
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialog.setAlwaysOnTop(false);
        dialog.setVisible(true);
        msgLabel.setBackground(panel.getBackground());
        System.out.println("ssse");

    }
    
    public void updateBar(int current) {
    	progressBar.setValue(current);
    }
    
    public void done() {
        // Close the dialog
        dialog.dispose();
    }
}