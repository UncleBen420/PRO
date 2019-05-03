/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author gaetan
 */
public class FilterWeather extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
    private final JPanel panel;
    private FilterTable table;
    private int index;
    private String weatherText;
    
    public FilterWeather(FilterTable tab, int index){
        this.table = tab;
        this.index = index;
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox weather = new JComboBox();
        weather.addItem("Sun");
        weather.addItem("Cloud");
        weather.addItem("Rain");
        
        JButton filter = new JButton("Filter");
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                weatherText = weather.getSelectedItem().toString();
                JOptionPane.showMessageDialog(null, "Filter " + weatherText);
            }
        });
        
        JButton delete = new JButton("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.delRow(this.getClass());
            }
        });
        
        JLabel label = new JLabel("Weather");
        
        panel.add(label);
        panel.add(weather);
        panel.add(filter);
        panel.add(delete);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        index = ((FilterWeather)value).getIndex();
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
        index = ((FilterWeather)value).getIndex();
        return panel;
    }
    
    public void setIndex(int index){
        this.index = index;
    }
    
    public int getIndex(){
        return index;
    }
}
