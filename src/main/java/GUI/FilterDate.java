/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import JTreeManager.JTreeManager;
import searchfilters.TreeFilterDate;

/**
 *
 * @author gaetan
 */
public class FilterDate extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1206766147859009078L;
	private JPanel panel;
    private FilterTable table;
    private int index;
    private String begin;
    private String end;
    private JTreeManager manager;
    private TreeFilterDate currentFilter;
    
    public FilterDate(FilterTable tab, int index, final JTreeManager manager){
    	
    	this.manager = manager;
    	currentFilter = null;
        this.table = tab;
        this.index = index;
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        final JFormattedTextField beginText = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.SHORT));
        beginText.setValue(new Date());
        
        final JFormattedTextField endText = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.SHORT));
        endText.setValue(new Date());
        
        JButton filter = new JButton("Filter");
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                begin = beginText.getText();
                end = endText.getText();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date beginDate = null;
                Date endDate = null;
                
                try {
					beginDate = df.parse("2017-02-23");
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
                try {
					endDate = df.parse("2017-04-23");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                if(currentFilter != null) {
                	manager.removeFiltre(currentFilter);
                }
                
                currentFilter = new TreeFilterDate(beginDate,endDate);
                manager.addFiltre(currentFilter);       

            }
        });
        
        JButton delete = new JButton("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	if(currentFilter != null) {
                	manager.removeFiltre(currentFilter);
                }
            	
                table.delRow(this.getClass());
            }
        });
        
        JLabel label = new JLabel("Date");
        
        panel.add(label);
        panel.add(beginText);
        panel.add(endText);
        panel.add(filter);
        panel.add(delete);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        index = ((FilterDate)value).getIndex();
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        }else{
            panel.setBackground(table.getBackground());
        }
        
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return this;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        index = ((FilterDate)value).getIndex();
        return panel;
    }
    
    public void setIndex(int index){
        this.index = index;
    }
    
    public int getIndex(){
        return index;
    }
}
