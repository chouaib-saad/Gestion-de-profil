package Formation.GestionFormation.Interfaces;


import Formation.GestionFormation.DAO.FormationDAO;
import Formation.GestionFormation.FormationTableModel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class IHMAffF extends JInternalFrame {

    private JTable jTable;
    private FormationTableModel tableModel;

    private FormationDAO formationDAO;

    // Constructor with no arguments
    public IHMAffF() {
        this(new FormationDAO()); // Use default constructor for FormationDAO
    }

    // Constructor with FormationDAO argument
    public IHMAffF(FormationDAO formationDAO) {
        this.formationDAO = formationDAO;

        setTitle("Affichage de formation");
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
            tableModel = new FormationTableModel(formationDAO.getAllFormations(), formationDAO);
            jTable.setModel(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des formations", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }
}
