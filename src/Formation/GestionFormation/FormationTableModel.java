package Formation.GestionFormation;


import Formation.GestionFormation.DAO.FormationDAO;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class FormationTableModel extends AbstractTableModel {

    private ArrayList<Object[]> data = new ArrayList<>();
    private ResultSetMetaData rsmd;
    private FormationDAO formationDAO;

    public FormationTableModel(ResultSet rs, FormationDAO formationDAO) {
        this.formationDAO = formationDAO;

        int columnCount;
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
    public int getRowCount() {
        return data.size();
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        try {
            return rsmd.getColumnName(column + 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
