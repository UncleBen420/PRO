/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TableRenderDemoProject/src/components/TableRenderDemo.java

package GUI;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author gaetan
 */
public class ViewerTable extends JPanel {
    private JTable table;

    public ViewerTable() {
        super(new GridLayout(1,1));
        Object[] columnNames = {"Animal", "Size", "Sexe", "Enter tunnel"};
        AbstractTableModel model = new AbstractTableModel(){
             private String[] columnNames = {"Animal", "Size", "Sexe", "Enter tunnel"};
            private Object[][] data ={{"", new Double(0), new Boolean(false), new Boolean(false)}};

             @Override
    public int getColumnCount() {
        return columnNames.length;
    }

             @Override
    public int getRowCount() {
        return data.length;
    }

             @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

             @Override
    public String getValueAt(int row, int col) {
        return "" + data[row][col];
    }

             @Override
    public Class getColumnClass(int c) {
        switch(c){
            case 0:
                return String.class;
            case 1:
                return Double.class;
            case 2:
                return Boolean.class;
            case 3:
                return Boolean.class;
            default:
                return Object.class;
        }
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

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
             @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
        };
        
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Set up column sizes.
        //initColumnSizes(table);
 
        //Fiddle with the Sport column's cell editors/renderers.
        setUpAnimalColumn(table, table.getColumnModel().getColumn(0));
 
        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    
    public void setUpAnimalColumn(JTable table,
                                 TableColumn animalColumn) {
        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Crapaud");
        comboBox.addItem("Tritton");
        animalColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }
    
   
}

