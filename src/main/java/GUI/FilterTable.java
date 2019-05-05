/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TableRenderDemoProject/src/components/TableRenderDemo.java
package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

import JTreeManager.JTreeManager;

/**
 *
 * @author gaetan
 */
public class FilterTable extends JPanel {

    private JTable table;
    String[] columnNames = {"Filters"};
    ArrayList<Object> rowData = new ArrayList<Object>();
    FilterWeather filterWeather = new FilterWeather(this, rowData.size());
    FilterChange filterChange = new FilterChange(this, rowData.size());
    FilterDate filterDate = new FilterDate(this, rowData.size(),null);
    FilterTag filterTag = new FilterTag(this, rowData.size());
    private JTreeManager manager;
    
    AbstractTableModel model = new AbstractTableModel() {
            

	    public int getColumnCount() {
		return 1;
	    }

            public String getColumnName(int column) {
                return columnNames[column];
            }

            public int getRowCount() {
                return rowData.size();
            }

            public Object getValueAt(int row, int column) {
                return rowData.get(row);
            }

            public Class getColumnClass(int column) {
                return (getValueAt(0, column).getClass());
            }

            public void setValueAt(Object value, int row, int column) {
                if(row >= rowData.size()){
                    return;
                }
                rowData.set(row, value);
            }

            /*
             * Don't need to implement this method unless your table's
             * editable.
             */
            @Override
            public boolean isCellEditable(int row, int col) {
                //Note that the data/cell address is constant,
                //no matter where the cell appears onscreen.
                return true;
            }
        };

    public FilterTable(JTreeManager manager) {
    	
        super(new GridLayout(1, 1));
        
        this.manager = manager;

        JTable table = new JTable(model){
            // override these 2 jtable methods so we can provide different values in a single column
            public TableCellEditor getCellEditor(int row, int column) {
                TableColumn tableColumn = getColumnModel().getColumn(column);
                TableCellEditor editor = tableColumn.getCellEditor();
                if (editor == null) {
                    editor = getDefaultEditor(getModel().getValueAt(row,column).getClass());
                }
                return editor;
            }

            public TableCellRenderer getCellRenderer(int row, int column) {
                TableColumn tableColumn = getColumnModel().getColumn(column);
                TableCellRenderer renderer = tableColumn.getCellRenderer();
                if (renderer == null) {
                    renderer = getDefaultRenderer(getModel().getValueAt(row,column).getClass());
                }
                return renderer;
            }
        };
        table.setDefaultRenderer(FilterWeather.class, new FilterWeather(this, rowData.size()));
        table.setDefaultRenderer(FilterChange.class, new FilterChange(this, rowData.size()));
        table.setDefaultRenderer(FilterDate.class, new FilterDate(this, rowData.size(),manager));
        table.setDefaultRenderer(FilterTag.class, new FilterTag(this, rowData.size()));
        
        table.setDefaultEditor(FilterWeather.class, new FilterWeather(this, rowData.size()));
        table.setDefaultEditor(FilterChange.class, new FilterChange(this, rowData.size()));
        table.setDefaultEditor(FilterDate.class, new FilterDate(this, rowData.size(),manager));
        table.setDefaultEditor(FilterTag.class, new FilterTag(this, rowData.size()));
        
        table.setRowHeight(40);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    
    public void addRow(String value){
        switch(value){
            case "Weather":
                rowData.add(new FilterWeather(this, rowData.size()));
                break;
            case "Date / Hour":
                rowData.add(new FilterDate(this, rowData.size(),manager));
                break;
            case "Change":
                rowData.add(new FilterChange(this, rowData.size()));
                break;
            case "Tag":
                rowData.add(new FilterTag(this, rowData.size()));
                break;
              
            default:
        }
        model.fireTableDataChanged();
    }
    
    public void delRow(Class test){
        
        for(int i = 0; i < rowData.size(); i++){
            if(test.isInstance(rowData.get(i))){
                rowData.remove(i);
                return;
            }
        }
        model.fireTableDataChanged();
    }
    
    public void clear(){
        rowData.clear();
        rowData.add("");
        model.fireTableDataChanged();
    }

}
