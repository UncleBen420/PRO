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
import javax.swing.*;
import javax.swing.table.*;

/**
 * Classe implémentant l'interface du tableau pour les tags
 * 
 * @author gaetan
 */
public class ViewerTable extends JPanel {

    private JTable table;
    String[] columnNames = {"Animal", "Size", "Male", "Enter tunnel"};
    ArrayList<ArrayList<Object>> rowData = new ArrayList<ArrayList<Object>>();
    ArrayList<Object> row = new ArrayList<Object>();
    
    AbstractTableModel model = new AbstractTableModel() {
            

            @Override
	    public int getColumnCount() {
		return columnNames.length;
	    }

            @Override
            public String getColumnName(int column) {
                return columnNames[column];
            }

            @Override
            public int getRowCount() {
                return rowData.size();
            }

            @Override
            public Object getValueAt(int row, int column) {
                return rowData.get(row).get(column);
            }

            @Override
            public Class getColumnClass(int column) {
                return (getValueAt(0, column).getClass());
            }

            @Override
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

    public ViewerTable() {
        super(new GridLayout(1, 1));
        row.add("");
        row.add(new Double(0));
        row.add(Boolean.FALSE);
        row.add(Boolean.FALSE);

        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Fiddle with the Sport column's cell editors/renderers.
        setUpAnimalColumn(table, table.getColumnModel().getColumn(0));

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    
    public void addRow(){
        rowData.add(new ArrayList<Object>(row));
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
    
    public ArrayList<ArrayList<String>> getData() {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        ArrayList<String> line = new ArrayList<String>();
        for(ArrayList<Object> list : rowData){
            for(Object data : list) {
                line.add(data + "");
            }
            result.add(new ArrayList<String>(line));
            line.clear();
        }
        
        return result;
    }

    public void setUpAnimalColumn(JTable table,
            TableColumn animalColumn) {
        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Crapaud");
        comboBox.addItem("Triton");
        comboBox.addItem("Grenouille");
        animalColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }
    
    public void setTags(ArrayList<ArrayList<String>> tags){
        rowData.clear();
        for(ArrayList<String> tag : tags){
            ArrayList<Object> rowTag = new ArrayList<Object>();
            for(int i = 1; i < 5; i++){
                switch(i){
                    case 1:
                        rowTag.add(tag.get(i));
                        break;
                    case 2:
                        rowTag.add(Double.valueOf(tag.get(i)));
                        break;
                    case 3:
                        rowTag.add(tag.get(i).equals("true"));
                        break;
                    case 4:
                        rowTag.add(tag.get(i).equals("true"));
                        break;
                }
            }
            rowData.add(new ArrayList<Object>(rowTag));
        }
        model.fireTableDataChanged();
    }

}
