package Formation.GestionFormation.Interfaces;

import Formation.GestionFormation.DAO.EnseignantDAO;
import Formation.GestionFormation.DemandeEnsModel;
import Formation.GestionFormation.Helpers.DemandeData;
import Formation.GestionFormation.Helpers.Enseignant;
import Formation.GestionFormation.Utils.EcouteurFocus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class IHMRechENS extends JInternalFrame {

    private String placeholderNumCarte = "Entrez le Numéro de carte CIN";
    private String placeholderNom = "Entrez le Nom";
    private String placeholderPrenom = "Entrez le Prénom";
    private String placeholderSpecialite = "Entrez votre specialité";

    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldSpecialite;
    private JButton boutonModifier;
    private JButton boutonSupprimer;
    private JButton boutonAnnuler;
    private JButton boutonAjouterDemande;
    private JButton boutonSupprimerDemande;
    private JComboBox<String> comboBoxFormations;
    private JComboBox<String> comboBoxGrade;
    private JLabel labelListeDemandes;
    private JTable tableDemandes;
    EnseignantDAO enseignantDAO = new EnseignantDAO();
    Enseignant enseignant;


    public IHMRechENS() {
        setTitle("Recherche d'enseignant");
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création des composants
        JLabel labelNumCarte = new JLabel("Numéro de carte CIN: ");
        JTextField textFieldNumCarte = new JTextField(20);
        textFieldNumCarte.setText(placeholderNumCarte);
        JButton boutonRecherche = new JButton("Recherche");

        JLabel labelNom = new JLabel("Nom:                                ");
        textFieldNom = new JTextField(20);
        textFieldNom.setText(placeholderNom);
        JLabel labelPrenom = new JLabel("Prénom:                         ");
        textFieldPrenom = new JTextField(20);
        textFieldPrenom.setText(placeholderPrenom);

        JLabel labelGrade = new JLabel("Grade:                            ");
        String[] grades = {"Assistant", "Maitre assistant", "Maitre de conferences", "Professeur de l'enseignement superieur", "Docteur"};
        comboBoxGrade = new JComboBox<>(grades);

        JLabel labelSpecialite = new JLabel("Spécialité:                     ");
        textFieldSpecialite = new JTextField(20);
        textFieldSpecialite.setText(placeholderSpecialite);

        boutonModifier = new JButton("Modifier");
        boutonSupprimer = new JButton("Supprimer");
        boutonAnnuler = new JButton("Annuler");
        boutonAjouterDemande = new JButton("Ajouter demande");
        boutonSupprimerDemande = new JButton("Supprimer demande");
        String [] formations = {"Formations"};
        comboBoxFormations = new JComboBox<>(formations); // Combobox : liste des formations disponible
        labelListeDemandes = new JLabel("Liste des demandes:");
        tableDemandes = new JTable(); // Remplacez ceci par un modèle de table approprié avec les données des demandes

        JLabel vide = new JLabel("                                                  ");



        textFieldNumCarte.addFocusListener(new EcouteurFocus(textFieldNumCarte, placeholderNumCarte));
        desactiverChampsEnseignant();


        boutonRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer le numéro de carte enseignant depuis le champ de texte
                int numCarte;

                if (textFieldNumCarte.getText().trim().equals(placeholderNumCarte)) {
                    JOptionPane.showMessageDialog(null, "Remplir le champ de numéro de carte d'enseignant d'abord !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    numCarte = Integer.parseInt(textFieldNumCarte.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Le numéro de carte enseignant doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Créer une instance de la classe FormationDAO pour utiliser la méthode rechercherEnseignant

                try {
                    // Appeler la méthode rechercherEnseignant
                    enseignant = enseignantDAO.rechercherEnseignant(numCarte);

                    // Vérifier si l'enseignant a été trouvé
                    if (enseignant != null) {
                        // Afficher les informations de l'enseignant dans les champs de texte correspondants
                        textFieldNom.setText(enseignant.getNom());
                        textFieldPrenom.setText(enseignant.getPrenom());
                        textFieldSpecialite.setText(enseignant.getSpecialite());
                        comboBoxGrade.setSelectedItem(enseignant.getGrade());
                        System.out.println(enseignant.getGrade());


                        try {
                            // Call the method to retrieve formation titles
                            String[] formationTitles = enseignantDAO.getFormationTitles();

                            // Update the JComboBox with the retrieved titles
                            comboBoxFormations.removeAllItems(); // Clear existing items
                            for (String title : formationTitles) {
                                comboBoxFormations.addItem(title);
                            }

                            // Enable the JComboBox
                            comboBoxFormations.setEnabled(true);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des formations", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }

                        updateDemandesTable();

                        // Activer les champs de l'enseignant
                        activerChampsEnseignant();
                    } else {
                        // Réinitialiser les labels des informations de l'enseignant
                        textFieldNom.setText(placeholderNom);
                        textFieldPrenom.setText(placeholderPrenom);
                        textFieldSpecialite.setText(placeholderSpecialite);
                        comboBoxGrade.setSelectedIndex(0);

                        // Désactiver les champs de l'enseignant
                        desactiverChampsEnseignant();

                        // Afficher un message indiquant que le numéro de carte enseignant n'a pas été trouvé
                        JOptionPane.showMessageDialog(null, "Numéro de carte enseignant non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    // Gérer les exceptions liées à la base de données
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la recherche de l'enseignant", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        boutonModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer le numéro de carte de l'enseignant depuis le champ de texte
                int numCarte;

                if(textFieldNumCarte.getText().trim().equals(placeholderNumCarte)) {
                    JOptionPane.showMessageDialog(null, "Remplir le champ du numéro de carte d'abord !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    numCarte = Integer.parseInt(textFieldNumCarte.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Le numéro de carte doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Récupérer les informations de l'enseignant à partir des champs de texte
                String nom = textFieldNom.getText();
                String prenom = textFieldPrenom.getText();
                String specialite = textFieldSpecialite.getText();
                String grade = comboBoxGrade.getSelectedItem().toString();


                try {
                    // Appeler la méthode modifierEnseignant
                    enseignantDAO.modifierEnseignant(numCarte, nom, prenom, specialite, grade);

                    // Afficher un message indiquant que l'enseignant a été modifié avec succès
                    JOptionPane.showMessageDialog(null, "Enseignant modifié avec succès !");

                    // Réinitialiser les champs de texte et désactiver les boutons
                    textFieldNumCarte.setText(placeholderNumCarte);
                    textFieldNom.setText(placeholderNom);
                    textFieldPrenom.setText(placeholderPrenom);
                    textFieldSpecialite.setText(placeholderSpecialite);
                    comboBoxGrade.setSelectedIndex(0);
                    desactiverChampsEnseignant(); // Désactiver les champs d'enseignant
                } catch (SQLException ex) {
                    // Gérer les exceptions liées à la base de données
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la modification de l'enseignant", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        boutonSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer le numéro de carte de l'enseignant depuis le champ de texte
                int numCarte;

                if(textFieldNumCarte.getText().trim().equals(placeholderNumCarte)) {
                    JOptionPane.showMessageDialog(null, "Remplir le champ du numéro de carte d'abord !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    numCarte = Integer.parseInt(textFieldNumCarte.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Le numéro de carte doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                try {
                    // Demander une confirmation avant de supprimer
                    int choix = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet enseignant ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                    if (choix == JOptionPane.YES_OPTION) {
                        // Appeler la méthode supprimerEnseignant
                        enseignantDAO.supprimerEnseignant(numCarte);

                        // Afficher un message indiquant que l'enseignant a été supprimé avec succès
                        JOptionPane.showMessageDialog(null, "Enseignant supprimé avec succès !");

                        // Réinitialiser les champs de texte
                        textFieldNumCarte.setText(placeholderNumCarte);
                        textFieldNom.setText(placeholderNom);
                        textFieldPrenom.setText(placeholderPrenom);
                        textFieldSpecialite.setText(placeholderSpecialite);
                        comboBoxGrade.setSelectedIndex(0);

                        desactiverChampsEnseignant();

                    }

                } catch (SQLException ex) {
                    // Gérer les exceptions liées à la base de données
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de l'enseignant", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        boutonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });





        boutonAjouterDemande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Récupérer l'ID de la formation sélectionnée
                String formationTitre = (String) comboBoxFormations.getSelectedItem();
                int idFormation;
                try {
                    idFormation = enseignantDAO.getFormationIdByTitre(formationTitre);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la récupération de l'ID de la formation", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Récupérer l'ID de l'étudiant (supposons que vous l'ayez déjà)
                int idEnseignant = enseignant.getId(); // Supposons que vous avez déjà cette information

                // Insérer les informations dans demandeetd
                try {
                    if(enseignantDAO.ajouterDemandeENS(idFormation, idEnseignant)){
                        JOptionPane.showMessageDialog(null, "Demande ajoutée avec succès !");
                        updateDemandesTable();
                    }else {
                        JOptionPane.showMessageDialog(null, "Demande déja existe !");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la demande", "Erreur", JOptionPane.ERROR_MESSAGE);
                }




            }
        });




        boutonSupprimerDemande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if a row is selected
                int selectedRow = tableDemandes.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Pas de demande sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                supprimerDemande(selectedRow);

            }
        });







        tableDemandes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = (JTable) e.getSource();

                //ajouter le listener si le droite de souris est cliqué:
                if (e.getButton() == MouseEvent.BUTTON3) {  //bouton droit de souris
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem supprimerFormation = new JMenuItem("Supprimer demande");
                    JMenuItem suppTous = new JMenuItem("Supprimer tous les demandes");
                    popupMenu.add(supprimerFormation);
                    popupMenu.add(suppTous);

                    popupMenu.show(table, e.getX(), e.getY());

                    supprimerFormation.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int choix = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer la demande pour ce utilisateur ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                            int row = tableDemandes.getSelectedRow();
                            if (choix == JOptionPane.YES_OPTION) {
                                //suppression:
                                if (row == -1) {
                                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner la demande à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                //supp formation here
                                supprimerDemande(row);


                            } else {
                                // Ne rien faire si l'utilisateur a choisi "Non"
                            }
                        }
                    });

                    //supprimer tous les données
                    suppTous.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int choix = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer tous les demandes ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                            //suppression:
                            if (choix == JOptionPane.YES_OPTION) {
                                // Supprimer tous les étudiants de la base de données en utilisant la méthode supprimerTous de la classe EtudiantDAO
                                int rowsAffected = enseignantDAO.supprimerDemande("demandeens","idEnseignant",enseignant.getId());
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(null, "Tous les demandes ont été supprimés avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                                    // Mise à jour de la table
                                    updateDemandesTable();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression des demandes.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                // Ne rien faire si l'utilisateur a choisi "Non"
                            }
                        }
                    });
                }
            }
        });



        // Création d'un panneau principal pour organiser les composants avec BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Création de panneaux pour les groupes de composants
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(labelNumCarte);
        panel1.add(textFieldNumCarte);
        panel1.add(boutonRecherche);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel2.add(labelNom);
        panel2.add(textFieldNom);
        panel2.add(boutonModifier);

        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel3.add(labelPrenom);
        panel3.add(textFieldPrenom);
        panel3.add(boutonSupprimer);


        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel4.add(labelSpecialite);
        panel4.add(textFieldSpecialite);
        panel4.add(boutonAnnuler);

        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel5.add(labelGrade);
        panel5.add(comboBoxGrade);



        JPanel panel6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel6.add(vide);
        panel6.add(boutonAjouterDemande);
        panel6.add(boutonSupprimerDemande);
        panel6.add(comboBoxFormations);

        JPanel panel7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel7.add(labelListeDemandes);
        panel7.add(new JScrollPane(tableDemandes));

        // Ajout des panneaux au panneau principal
        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);
        mainPanel.add(panel4);
        mainPanel.add(panel5);
        mainPanel.add(panel6);
        mainPanel.add(panel7);
//        mainPanel.add(new JScrollPane(tableDemandes)); // Ajout d'un JScrollPane pour rendre la table scrollable

        // Ajout du panneau principal à la fenêtre interne
        getContentPane().add(mainPanel);

        setVisible(true); // Afficher la fenêtre
    }

    // Méthode pour activer les champs d'enseignant après une recherche réussie
    private void activerChampsEnseignant() {
        // Activer les champs d'enseignant
        textFieldNom.setEnabled(true);
        textFieldPrenom.setEnabled(true);
        textFieldSpecialite.setEnabled(true);
        boutonModifier.setEnabled(true);
        boutonSupprimer.setEnabled(true);
        boutonAjouterDemande.setEnabled(true);
        boutonSupprimerDemande.setEnabled(true);
        comboBoxGrade.setEnabled(true);
        comboBoxFormations.setEnabled(true);
        tableDemandes.setEnabled(true);



    }


        // Méthode pour désactiver les champs d'enseignant
    private void desactiverChampsEnseignant() {
        // Désactiver les champs d'enseignant
        textFieldNom.setEnabled(false);
        textFieldPrenom.setEnabled(false);
        textFieldSpecialite.setEnabled(false);
        boutonModifier.setEnabled(false);
        boutonSupprimer.setEnabled(false);
        boutonAjouterDemande.setEnabled(false);
        boutonSupprimerDemande.setEnabled(false);
        comboBoxGrade.setEnabled(false);
        comboBoxFormations.setEnabled(false);

        // Clear the data from the table and disable it
        tableDemandes.setModel(new DefaultTableModel()); // Clear data
        tableDemandes.setEnabled(false); // Disable table
    }



    private void updateDemandesTable() {
        try {
            // Fetch demand data for the specific student
            List<DemandeData> demandesData = enseignantDAO.getAllDemandesForEnseignats(enseignant.getId());

            // Create a model for the table with demand data
            DemandeEnsModel demandeEnsModel = new DemandeEnsModel(demandesData);

            // Set the model to the table
            tableDemandes.setModel(demandeEnsModel);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des demandes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void supprimerDemande(int selectedRow){

        // Get the selected formation title
        String selectedFormationTitle = (String) tableDemandes.getValueAt(selectedRow, 0); // Assuming the formation title is in the first column

        // Get the student ID
        int enseignantId = enseignant.getId(); // Assuming you have access to the student's ID

        // Get the formation ID
        try {
            int formationId = enseignantDAO.getFormationIdByTitre(selectedFormationTitle);

            // Delete the demand from the database and update the table
            enseignantDAO.deleteDemandeENS(formationId, enseignantId);
            JOptionPane.showMessageDialog(null, "Demande supprimée avec succès !");
            updateDemandesTable(); // Refresh the table
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la demande", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }




}
