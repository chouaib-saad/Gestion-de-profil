package Formation.GestionFormation.Interfaces;

import Formation.GestionFormation.DAO.EnseignantDAO;
import Formation.GestionFormation.DAO.EtudiantDAO;
import Formation.GestionFormation.EtudiantTableModel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class IHMAffETD extends JInternalFrame {

    private JTable jTable;
    private EtudiantTableModel tableModel;
    private EtudiantDAO etudiantDAO;

    // Constructor with no arguments
    public IHMAffETD() {
        this(new EtudiantDAO()); // Use default constructor for EtudiantDAO
    }

    // Constructor with EtudiantDAO argument
    public IHMAffETD(EtudiantDAO etudiantDAO) {
        this.etudiantDAO = etudiantDAO;

        setTitle("Affichage des étudiants");
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
            tableModel = new EtudiantTableModel(etudiantDAO.getAllEtudiants());
            jTable.setModel(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des étudiants", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }
}
