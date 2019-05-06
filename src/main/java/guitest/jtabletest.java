package guitest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import GUI.FilterDate;
import GUI.FiltersPanel;
import JTreeManager.JTreeManager;

public class jtabletest extends JFrame {
    public jtabletest() {
        super();
 
        setTitle("JTable basique dans un JPanel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setMinimumSize(new Dimension(200,200));
 

 JPanel tableau = new JPanel();
 
 tableau.setBackground(Color.cyan);
 tableau.setSize(200, 200);
 
JPanel tableau2 = new JPanel();
 
 tableau2.setBackground(Color.DARK_GRAY);
 tableau2.setMinimumSize(new Dimension(200,200));

 final JPanel a = new JPanel();
 final JPanel b = new JPanel();
 
 final JTreeManager tree = new JTreeManager();
 
 DateFiltreTest ss = new DateFiltreTest(tree);
 DateFiltreTest s2s = new DateFiltreTest(tree);
 DateFiltreTest s3s = new DateFiltreTest(tree);
 DateFiltreTest s4s = new DateFiltreTest(tree);
 DateFiltreTest s5s = new DateFiltreTest(tree);
 
 
 a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
 b.setBounds(10, 10, 100, 100);
 b.setLayout(new BoxLayout(b,BoxLayout.Y_AXIS));
 JScrollPane hg = new JScrollPane(tree);
 
 final JComboBox jComboBox1 = new JComboBox();
jComboBox1.setModel( new javax.swing.DefaultComboBoxModel<>(new String[] { "Date / Hour", "Weather", "Change", "Tag" }));

 JButton jButton1 = new JButton();

 
 b.add(ss);
 b.add(s2s);
 b.add(s3s);
 b.remove(s3s);
 b.repaint();
 b.add(s4s);
 b.add(s5s);
 
 

 final JScrollPane dd = new JScrollPane(b);
 
 
 jButton1.setText("Add");
 final JFrame f = this;
  jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
          jButton1MouseClicked(evt);
      }

 	private void jButton1MouseClicked(MouseEvent evt) {
 		System.out.println(jComboBox1.getSelectedItem().toString());
 		b.add(new DateFiltreTest(tree));
 		dd.repaint();
 		b.repaint();
 		a.repaint();
 		f.repaint();		
 		
 	}
  });
 
 a.add(hg);
 a.add(jComboBox1);
 a.add(jButton1);
 a.add(dd);

 
 
        getContentPane().add(a, BorderLayout.CENTER);
 
        pack();
    }
 
    public static void main(String[] args) {
    	
    	
    	JTreeManager manager = new JTreeManager();

    	FiltersPanel fil = new FiltersPanel(manager);
        JFrame j = new JFrame();
        
        j.add(manager);
        j.add(fil);
        j.setVisible(true);
        
    }
}