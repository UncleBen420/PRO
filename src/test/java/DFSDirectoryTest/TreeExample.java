package DFSDirectoryTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


import JTreeManager.JTreeManager;
import searchfilters.TreeFilterDate;
public class TreeExample extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TreeExample()
    {
    	
    	Properties properties = new Properties();
		FileReader fr = null;
		try {
			fr = new FileReader("conf.properties");
			properties.load(fr);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}   	
    	JTreeManager manager = new JTreeManager();
    	
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
   	
    	TreeFilterDate filtreDate = null;
    	TreeFilterDate filtreDate2 = null;
		try {
			filtreDate = new TreeFilterDate(df.parse("2017-03-23"),df.parse("2017-04-23"));
			filtreDate2 = new TreeFilterDate(df.parse("2017-04-23"),df.parse("2017-04-23"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    	manager.addFiltre(filtreDate);
    	manager.addFiltre(filtreDate2);
    	
    	manager.removeFiltre(filtreDate);
    	manager.removeFiltre(filtreDate2);
    	
    	this.add(new JScrollPane(manager));
		
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JTree Example");       
        this.pack();
        this.setVisible(true);
        
        
        
        
        
        
        
    }
     
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TreeExample();
            }
        });
    }       
}