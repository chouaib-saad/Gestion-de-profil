package database;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyTableModel extends AbstractTableModel {

    private ArrayList<Object[]> data = new ArrayList<>();
    private ResultSetMetaData rsmd;
    EtudiantDAO dao;

    MyTableModel(ResultSet rs, EtudiantDAO dao)  {

        this.dao = dao;

        int columnCount = 0;
        try {

            rsmd = rs.getMetaData();

            columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                data.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int getColumnCount() {
        try {
            return rsmd.getColumnCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    @Override
    public String getColumnName(int col) {
        try {
            return rsmd.getColumnName(col + 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return !getColumnName(col).equalsIgnoreCase("cin");
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        String nom= getValueAt(row,columnNameToIndex("nom")).toString();
        String prenom= getValueAt(row,columnNameToIndex("prenom")).toString();
        double moyenne= Double.parseDouble(getValueAt(row,columnNameToIndex("moyenne")).toString());
        int cin= Integer.parseInt(getValueAt(row,columnNameToIndex("cin")).toString());

        if(getColumnName(col).equalsIgnoreCase("nom"))
            nom=value.toString();
        if(getColumnName(col).equalsIgnoreCase("prenom"))
            prenom=value.toString();

        if(getColumnName(col).equalsIgnoreCase("moyenne"))
            moyenne=Double.parseDouble(value.toString());



        int a = dao.modifierEtudiant(nom,prenom,cin,moyenne);

        if(a>0) {
            data.get(row)[col] = value;
            fireTableCellUpdated(row, col);
        }

    }

    int columnNameToIndex(String columName){

        for(int i=0;i<getColumnCount();i++){

            if(getColumnName(i).equalsIgnoreCase(columName)) {
                return i;

            }
        }

        return -1;


    }

    public void insertEtudiant(String nom, String prenom, int cin, double moyenne) {

        //Maj B
        int a = dao.insertEtudiant(nom,prenom,cin,moyenne);
        if (a == 1062 ){

            JOptionPane.showMessageDialog(null,"Erreur lors de l'ajout : Le cin  existe déjà.");

        }
        else if(a>0){

            Object[] rowData = {nom, prenom,cin, moyenne};
            data.add(rowData);
            fireTableRowsInserted(data.size() - 1, data.size() - 1);
            fireTableDataChanged();
            JOptionPane.showMessageDialog(null,"ajout avec succés");

        }
        else {

            JOptionPane.showMessageDialog(null,"Non inséré..");
        }


    }



    //methode pour vider le tableau
    public void viderTable() {
        data.clear(); // Supprime toutes les données
        fireTableDataChanged(); // Notifie le modèle que les données ont changé
    }



    //methode pour supprimer tous les etudiants de tableau
    public void supprimerEtudiant(int row) {
        data.remove(row); // Supprime la ligne à l'indice spécifié
        fireTableRowsDeleted(row, row); // Notifie le modèle qu'une ligne a été supprimée
    }






}
