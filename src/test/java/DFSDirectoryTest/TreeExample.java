package DFSDirectoryTest;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


import JTreeManager.JTreeManager;
import meteoAPI.TYPEMETEO;
import searchfilters.MeteoTreeFilter;
import searchfilters.TagTreeFilter;
import searchfilters.TemperatureTreeFilter;
import searchfilters.DateTreeFilter;

/**
 *
 * @author gaetan
 */
public class TreeExample extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     *
     */
    public TreeExample()
    {
    	
	
    	JTreeManager manager = new JTreeManager();
    	
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
   	
    	DateTreeFilter filtreDate = null;
    	DateTreeFilter filtreDate2 = null;
		try {
			filtreDate = new DateTreeFilter(df.parse("2017-03-23"),df.parse("2017-04-23"));
			filtreDate2 = new DateTreeFilter(df.parse("2017-04-23"),df.parse("2017-04-23"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    	manager.addFiltre(filtreDate);
    	manager.addFiltre(filtreDate2);
    	
    	manager.removeFiltre(filtreDate);
    	manager.removeFiltre(filtreDate2);
    	
    	MeteoTreeFilter meteo = new MeteoTreeFilter(TYPEMETEO.DEGAGE, false);
    	
    	TemperatureTreeFilter tf = new TemperatureTreeFilter(1. , 2.);
    	
    	TagTreeFilter ttf = new TagTreeFilter(true);
    	
    	manager.addFiltre(ttf);
    	
    	this.add(new JScrollPane(manager));
		
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JTree Example");       
        this.pack();
        this.setVisible(true);
        
        
    }
     
    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TreeExample();
            }
        });
    }       
}