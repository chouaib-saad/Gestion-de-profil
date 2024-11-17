package Formation.GestionFormation;

import Formation.GestionFormation.Helpers.DemandeData;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class DemandeEtdModel extends AbstractTableModel {


    private ArrayList<DemandeData> demandes;
    private final String[] columnNames = {"Nom", "Lieu", "Date"};

    public DemandeEtdModel(List<DemandeData> demandes) {
        this.demandes = new ArrayList<>(demandes);
    }

    @Override
    public int getRowCount() {
        return demandes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DemandeData demandeData = demandes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return demandeData.getTitreFormation();
            case 1:
                return demandeData.getLieuFormation();
            case 2:
                return demandeData.getDateFormation();
            default:
                return null; // Or throw an exception
        }
    }
}
