package Formation.GestionFormation.Interfaces;

import Formation.GestionFormation.DAO.EnseignantDAO;
import Formation.GestionFormation.EnseignantTableModel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class IHMAffENS extends JInternalFrame {

    private JTable jTable;
    private EnseignantTableModel tableModel;
    private EnseignantDAO enseignantDAO;

    // Constructor with no arguments
    public IHMAffENS() {
        this(new EnseignantDAO()); // Use default constructor for FormationDAO
    }

    // Constructor with FormationDAO argument
    public IHMAffENS(EnseignantDAO enseignantDAO) {
        this.enseignantDAO = enseignantDAO;

        setTitle("Affichage des enseignants");
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du tableau
        jTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(jTable);
        add(scrollPane, BorderLayout.CENTER);

        // Chargement des données dans le tableau
        try {
            tableModel = new EnseignantTableModel(enseignantDAO.getAllEnseignants());
            jTable.setModel(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des enseignants", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }
}
