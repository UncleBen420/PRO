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
import java.util.function.Function;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Classe implementant l'interface du tableau pour les tags
 *
 * @author Groupe PRO B-9
 */
public class ViewerTable extends JPanel {

    private static final long serialVersionUID = 8486919786175345928L;
    private final JTable table;
    private String[] columnNames = {"Animal", "Size", "Male", "Enter tunnel"};
    private ArrayList<ArrayList<Object>> rowData;
    private final ArrayList<Object> row;

    AbstractTableModel model = new AbstractTableModel() {

        private static final long serialVersionUID = -2073671200932220328L;

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
        public Class<? extends Object> getColumnClass(int column) {
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

    /**
     * Constructeur
     */
    public ViewerTable() {
        super(new GridLayout(1, 1));
        this.row = new ArrayList<>();
        this.rowData = new ArrayList<>();
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

    /**
     * Ajoute un ligne au tableau
     */
    public void addRow() {
        rowData.add(new ArrayList<>(row));
        model.fireTableDataChanged();
    }

    /**
     * Supprime une ligne au tableau
     */
    public void delRow() {
        rowData.remove(rowData.size() - 1);
        model.fireTableDataChanged();
    }

    /**
     * Vide le tableau
     */
    public void clear() {
        rowData.clear();
        rowData.add(new ArrayList<>(row));
        model.fireTableDataChanged();
    }

    /**
     * Retourne la valeur des cases du tableau
     *
     * @return Valeurs des cases du tableau
     */
    public ArrayList<ArrayList<String>> getData() {
        ArrayList<ArrayList<String>> result;
        result = new ArrayList<>();
        ArrayList<String> line;
        line = new ArrayList<>();
        rowData.stream().map((ArrayList<Object> list) -> {
            list.forEach((data) -> {
                line.add(data + "");
            });
            return list;
        }).map((ArrayList<Object> _item) -> {
            result.add(new ArrayList<>(line));
            return _item;
        }).forEachOrdered((_item) -> {
            line.clear();
        });

        return result;
    }

    /**
     * Set la combobox
     *
     * @param table Tableau
     * @param animalColumn Colonne contenant la combobox
     */
    public void setUpAnimalColumn(JTable table,
            TableColumn animalColumn) {
        //Set up the editor for the sport cells.
        JComboBox<String> comboBox;
        comboBox = new JComboBox<>();
        comboBox.addItem("Crapaud");
        comboBox.addItem("Triton");
        comboBox.addItem("Grenouille");
        animalColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    /**
     * Remplis le tableau avec les tags presents sur l'image
     *
     * @param tags Tags de l'image
     */
    public void setTags(ArrayList<ArrayList<String>> tags) {
        rowData.clear();
        for (ArrayList<String> tag : tags) {
            ArrayList<Object> rowTag;
            rowTag = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                switch (i) {
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
            rowData.add(new ArrayList<>(rowTag));
        }
        model.fireTableDataChanged();
    }

}
