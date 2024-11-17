package Formation.GestionFormation.Interfaces;

import Formation.GestionFormation.DAO.EtudiantDAO;
import Formation.GestionFormation.DemandeEtdModel;
import Formation.GestionFormation.Helpers.DemandeData;
import Formation.GestionFormation.Helpers.Etudiant;
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

public class IHMRechETD extends JInternalFrame {

    private String placeholderNumCarte = "Entrez le Numéro de carte ETD";
    private String placeholderNom = "Entrez le Nom";
    private String placeholderPrenom = "Entrez le Prénom";

    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JComboBox<String> comboBoxFiliere;
    private JComboBox<Integer> comboBoxNiveau;
    private JComboBox<Integer> comboBoxGroupe;
    private JButton boutonModifier;
    private JButton boutonSupprimer;
    private JButton boutonAnnuler;
    private JButton boutonAjouterDemande;
    private JButton boutonSupprimerDemande;
    private JComboBox<String> comboBoxFormations;
    private JLabel labelListeDemandes;
    private JTable tableDemandes;

    //tableau dynamique
    private DemandeEtdModel tableModel;

    EtudiantDAO etudiantDAO = new EtudiantDAO();

    Etudiant etudiant;

    public IHMRechETD() {
        setTitle("Recherche d'étudiant");
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création des composants
        JLabel labelNumCarte = new JLabel("Numéro de carte ETD: ");
        JTextField textFieldNumCarte = new JTextField(20);
        textFieldNumCarte.setText(placeholderNumCarte);
        JButton boutonRecherche = new JButton("Recherche");

        JLabel labelNom = new JLabel("Nom:                                ");
        textFieldNom = new JTextField(20);
        textFieldNom.setText(placeholderNom);
        JLabel labelPrenom = new JLabel("Prénom:                         ");
        textFieldPrenom = new JTextField(20);
        textFieldPrenom.setText(placeholderPrenom);

        JLabel labelFiliere = new JLabel("Filière:                ");
        String[] filiereOptions = {"FIA", "ISI", "SI", "EAA", "GE", "GM", "MPI", "Autre"};
        comboBoxFiliere = new JComboBox<>(filiereOptions);

        JLabel labelNiveau = new JLabel("Niveau:              ");
        Integer[] niveauOptions = {1, 2, 3};
        comboBoxNiveau = new JComboBox<>(niveauOptions);

        JLabel labelGroupe = new JLabel("Groupe:             ");
        Integer[] groupeOptions = {1, 2, 3, 4, 5};
        comboBoxGroupe = new JComboBox<>(groupeOptions);

        boutonModifier = new JButton("Modifier");
        boutonSupprimer = new JButton("Supprimer");
        boutonAnnuler = new JButton("Annuler");

        boutonAjouterDemande = new JButton("Ajouter demande");
        boutonSupprimerDemande = new JButton("Supprimer demande");
        String[] formations =  {"Formations"};
        comboBoxFormations = new JComboBox<>(formations); // Combobox : liste des formations disponible

        labelListeDemandes = new JLabel("Liste des demandes:");
        tableDemandes = new JTable(); // Remplacez ceci par un modèle de table approprié avec les données des demandes

        JLabel vide1 = new JLabel("           ");
        JLabel vide2 = new JLabel("           ");
        JLabel vide3 = new JLabel("           ");
        JLabel vide4 = new JLabel("                                                  ");



        textFieldNumCarte.addFocusListener(new EcouteurFocus(textFieldNumCarte, placeholderNumCarte));
        desactiverChampsEtudiant();


        // Ajout des écouteurs d'événements aux boutons
        boutonRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer le numéro de carte étudiant depuis le champ de texte
                int numCarte;

                if (textFieldNumCarte.getText().trim().equals(placeholderNumCarte)) {
                    JOptionPane.showMessageDialog(null, "Remplir le champ de numéro de carte d'étudiant d'abord !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    numCarte = Integer.parseInt(textFieldNumCarte.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Le numéro de carte étudiant doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Créer une instance de la classe FormationDAO pour utiliser la méthode rechercherEtudiant

                try {
                    // Appeler la méthode rechercherEtudiant
                    etudiant = etudiantDAO.rechercherEtudiant(numCarte);

                    // Vérifier si l'étudiant a été trouvé
                    if (etudiant != null) {
                        // Afficher les informations de l'étudiant dans les champs de texte correspondants
                        textFieldNom.setText(etudiant.getNom());
                        textFieldPrenom.setText(etudiant.getPrenom());
                        comboBoxFiliere.setSelectedItem(etudiant.getFiliere());
                        comboBoxNiveau.setSelectedItem((etudiant.getNiveau()));
                        comboBoxGroupe.setSelectedItem((etudiant.getGroupe()));

                        // Activer les boutons modifier et supprimer
                        //boutonModifier.setEnabled(true);
                        //boutonSupprimer.setEnabled(true);


                        //**the operation of the comboboxformation liste of formations implmemented here .. ** :
                        // Inside the ActionListener of boutonRecherche
                        try {
                            // Call the method to retrieve formation titles
                            String[] formationTitles = etudiantDAO.getFormationTitles();

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


                        activerChampsEtudiant();



                    } else {
                        // Réinitialiser les labels des informations de l'étudiant
                        textFieldNom.setText(placeholderNom);
                        textFieldPrenom.setText(placeholderPrenom);
                        comboBoxFiliere.setSelectedIndex(0);
                        comboBoxNiveau.setSelectedIndex(0);
                        comboBoxGroupe.setSelectedIndex(0);


                        // Désactiver les boutons modifier et supprimer
                        // boutonModifier.setEnabled(false);
                        // boutonSupprimer.setEnabled(false);

                        desactiverChampsEtudiant();

                        // Afficher un message indiquant que le numéro de carte étudiant n'a pas été trouvé
                        JOptionPane.showMessageDialog(null, "Numéro de carte étudiant non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    // Gérer les exceptions liées à la base de données
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la recherche de l'étudiant", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });





        boutonModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer le numéro de carte de l'étudiant depuis le champ de texte
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

                // Récupérer les informations de l'étudiant à partir des champs de texte
                String nom = textFieldNom.getText();
                String prenom = textFieldPrenom.getText();
                String filiere = comboBoxFiliere.getSelectedItem().toString();
                int niveau = Integer.parseInt(comboBoxNiveau.getSelectedItem().toString());
                int groupe = Integer.parseInt(comboBoxGroupe.getSelectedItem().toString());

                // Créer une instance de la classe FormationDAO pour utiliser la méthode modifierEtudiant
                try {
                    // Appeler la méthode modifierEtudiant
                    etudiantDAO.modifierEtudiant(numCarte, nom, prenom, filiere, niveau, groupe);

                    // Afficher un message indiquant que l'étudiant a été modifié avec succès
                    JOptionPane.showMessageDialog(null, "Étudiant modifié avec succès !");

                    // Réinitialiser les champs de texte et désactiver les boutons
                    textFieldNumCarte.setText(placeholderNumCarte);
                    textFieldNom.setText(placeholderNom);
                    textFieldPrenom.setText(placeholderPrenom);
                    comboBoxFiliere.setSelectedIndex(0);
                    comboBoxNiveau.setSelectedIndex(0);
                    comboBoxGroupe.setSelectedIndex(0);
                    desactiverChampsEtudiant(); // Désactiver les champs d'étudiant
                } catch (SQLException ex) {
                    // Gérer les exceptions liées à la base de données
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la modification de l'étudiant", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });





        boutonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });



        boutonSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer le numéro de carte étudiant depuis le champ de texte
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

                // Créer une instance de la classe FormationDAO pour utiliser la méthode supprimerEtudiant

                try {
                    // Demander une confirmation avant de supprimer
                    int choix = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet étudiant ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                    if (choix == JOptionPane.YES_OPTION) {
                        // Appeler la méthode supprimerEtudiant
                        etudiantDAO.supprimerEtudiant(numCarte);

                        // Afficher un message indiquant que l'étudiant a été supprimé avec succès
                        JOptionPane.showMessageDialog(null, "Étudiant supprimé avec succès !");

                        // Réinitialiser les champs de texte et désactiver les boutons
                        textFieldNumCarte.setText("");
                        textFieldNom.setText(placeholderNom);
                        textFieldPrenom.setText(placeholderPrenom);
                        comboBoxFiliere.setSelectedItem("FIA");
                        comboBoxNiveau.setSelectedItem("1");
                        comboBoxGroupe.setSelectedItem("1");
                        desactiverChampsEtudiant();
                    }
                } catch (SQLException ex) {
                    // Gérer les exceptions liées à la base de données
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de l'étudiant", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });




        boutonAjouterDemande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Récupérer l'ID de la formation sélectionnée
                String formationTitre = (String) comboBoxFormations.getSelectedItem();
                int idFormation;
                try {
                    idFormation = etudiantDAO.getFormationIdByTitre(formationTitre);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la récupération de l'ID de la formation", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Récupérer l'ID de l'étudiant (supposons que vous l'ayez déjà)
                int idEtudiant = etudiant.getId(); // Supposons que vous avez déjà cette information

                // Insérer les informations dans demandeetd
                try {
                    if(etudiantDAO.ajouterDemandeETD(idFormation, idEtudiant)){
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
                                int rowsAffected = etudiantDAO.supprimerDemande("demandeetd","idEtudiant",etudiant.getId());
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
        panel4.add(labelFiliere);
        panel4.add(vide1);
        panel4.add(comboBoxFiliere);
        panel4.add(boutonAnnuler);

        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel5.add(labelNiveau);
        panel5.add(vide2);
        panel5.add(comboBoxNiveau);

        JPanel panel6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel6.add(labelGroupe);
        panel6.add(vide3);
        panel6.add(comboBoxGroupe);

        JPanel panel7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel7.add(vide4);
        panel7.add(boutonAjouterDemande);
        panel7.add(boutonSupprimerDemande);
        panel7.add(comboBoxFormations);


        JPanel panel8 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel8.add(labelListeDemandes);
        panel8.add(new JScrollPane(tableDemandes));

        // Ajout des panneaux au panneau principal
        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);
        mainPanel.add(panel4);
        mainPanel.add(panel5);
        mainPanel.add(panel6);
        mainPanel.add(panel7);
        mainPanel.add(panel8);
//        mainPanel.add(new JScrollPane(tableDemandes)); // Ajout d'un JScrollPane pour rendre la table scrollable

        // Ajout du panneau principal à la fenêtre interne
        getContentPane().add(mainPanel);

        setVisible(true); // Afficher la fenêtre
    }






    private void updateDemandesTable() {
        try {
            // Fetch demand data for the specific student
            List<DemandeData> demandesData = etudiantDAO.getAllDemandesForEtudiant(etudiant.getId());

            // Create a model for the table with demand data
            DemandeEtdModel demandeEtdModel = new DemandeEtdModel(demandesData);


            // Set the model to the table
            tableDemandes.setModel(demandeEtdModel);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des demandes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }



    // Méthode pour activer les champs d'étudiant après une recherche réussie
    private void activerChampsEtudiant() {
        // Activer les champs d'étudiant
        textFieldNom.setEnabled(true);
        textFieldPrenom.setEnabled(true);
        comboBoxFiliere.setEnabled(true);
        comboBoxNiveau.setEnabled(true);
        comboBoxGroupe.setEnabled(true);
        boutonModifier.setEnabled(true);
        boutonSupprimer.setEnabled(true);
        boutonAjouterDemande.setEnabled(true);
        boutonSupprimerDemande.setEnabled(true);
        comboBoxFormations.setEnabled(true);
        tableDemandes.setEnabled(true);

    }


    private void desactiverChampsEtudiant() {
        // Désactivé les champs d'étudiant
        textFieldNom.setEnabled(false);
        textFieldPrenom.setEnabled(false);
        comboBoxFiliere.setEnabled(false);
        comboBoxNiveau.setEnabled(false);
        comboBoxGroupe.setEnabled(false);
        boutonModifier.setEnabled(false);
        boutonSupprimer.setEnabled(false);
        boutonAjouterDemande.setEnabled(false);
            boutonSupprimerDemande.setEnabled(false);
        comboBoxFormations.setEnabled(false);

        // Clear the data from the table and disable it
        tableDemandes.setModel(new DefaultTableModel()); // Clear data
        tableDemandes.setEnabled(false); // Disable table


    }


    private void supprimerDemande(int selectedRow){


        // Get the selected formation title
        String selectedFormationTitle = (String) tableDemandes.getValueAt(selectedRow, 0); // Assuming the formation title is in the first column

        // Get the student ID
        int studentId = etudiant.getId(); // Assuming you have access to the student's ID

        // Get the formation ID
        try {
            int formationId = etudiantDAO.getFormationIdByTitre(selectedFormationTitle);

            // Delete the demand from the database and update the table
            etudiantDAO.deleteDemandeETD(formationId, studentId);
            JOptionPane.showMessageDialog(null, "Demande supprimée avec succès !");
            updateDemandesTable(); // Refresh the table
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la demande", "Erreur", JOptionPane.ERROR_MESSAGE);
        }


    }


}
