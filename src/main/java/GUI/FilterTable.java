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

/**
 *
 * @author gaetan
 */
public class FilterTable extends JPanel {

    private JTable table;
    String[] columnNames = {"Filters"};
    ArrayList<ArrayList<Object>> rowData = new ArrayList<ArrayList<Object>>();
    ArrayList<Object> row = new ArrayList<Object>();
    
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
                return rowData.get(row).get(column);
            }

            public Class getColumnClass(int column) {
                return (getValueAt(0, column).getClass());
            }

            public void setValueAt(Object value, int row, int column) {
                rowData.get(row).set(column, value);
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

    public FilterTable() {
        super(new GridLayout(1, 1));
        
        row.add("");
        
        rowData.add(new ArrayList<Object>(row));

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
        table.setDefaultRenderer(FilterWeather.class, new FilterWeather());
        table.setDefaultRenderer(FilterChange.class, new FilterChange());
        table.setDefaultRenderer(FilterDate.class, new FilterDate());
        table.setDefaultRenderer(FilterTag.class, new FilterTag());
        
        table.setDefaultEditor(FilterWeather.class, new FilterWeather());
        table.setDefaultEditor(FilterChange.class, new FilterChange());
        table.setDefaultEditor(FilterDate.class, new FilterDate());
        table.setDefaultEditor(FilterTag.class, new FilterTag());
        
        table.setRowHeight(40);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    
    public void addRow(String value){
        row.clear();
        switch(value){
            case "Weather":
                row.add(new FilterWeather());
                rowData.add(new ArrayList<Object>(row));
                break;
            case "Date / Hour":
                row.add(new FilterDate());
                rowData.add(new ArrayList<Object>(row));
                break;
            case "Change":
                row.add(new FilterChange());
                rowData.add(new ArrayList<Object>(row));
                break;
            case "Tag":
                row.add(new FilterTag());
                rowData.add(new ArrayList<Object>(row));
                break;
              
            default:
        }
        model.fireTableDataChanged();
    }
    
    public void delRow(){
        rowData.remove(rowData.size()-1);
        model.fireTableDataChanged();
    }
    
    public void clear(){
        rowData.clear();
        rowData.add(new ArrayList<Object>(row));
        model.fireTableDataChanged();
    }

}
