package Formation.GestionFormation.Interfaces;

import Formation.GestionFormation.DAO.FormationDAO;
import Formation.GestionFormation.Helpers.Formation;
import Formation.GestionFormation.Utils.EcouteurFocus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class IHMRechF extends JInternalFrame {

    private String placeholderRef = "Entrez la Reference";
    private String placeholderTitre = "Entrez le Titre";
    private String placeholderLieu = "Entrez le lieu";
    private String placeholderDate = "AAAA/MM/JJ";

    FormationDAO formationDAO = new FormationDAO();

    public IHMRechF() {
        setTitle("Recherche de formation");
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création des composants
        JLabel labelReference = new JLabel("Reference:");
        JTextField textFieldReference = new JTextField(20);
        textFieldReference.setText(placeholderRef);
        JButton boutonRecherche = new JButton("Recherche");

        JLabel labelTitre = new JLabel("Titre:");
        JTextField textFieldTitre = new JTextField(20);
        textFieldTitre.setText(placeholderTitre);
        textFieldTitre.setEnabled(false); // Désactivation du champ
        JButton boutonModifier = new JButton("Modifier");
        boutonModifier.setEnabled(false); // Désactivation du bouton

        JLabel labelLieu = new JLabel("Lieu:");
        JTextField textFieldLieu = new JTextField(20);
        textFieldLieu.setText(placeholderLieu);
        textFieldLieu.setEnabled(false); // Désactivation du champ
        JButton boutonSupprimer = new JButton("Supprimer");
        boutonSupprimer.setEnabled(false); // Désactivation du bouton

        JLabel labelDate = new JLabel("Date:");
        JTextField textFieldDate = new JTextField(20);
        textFieldDate.setText(placeholderDate);
        textFieldDate.setEnabled(false); // Désactivation du champ
        JButton boutonAnnuler = new JButton("Annuler");

        JLabel labelCertification = new JLabel("Certification:");
        JCheckBox checkBoxCertification = new JCheckBox();
        checkBoxCertification.setEnabled(false); // Désactivation de la case à cocher


        textFieldReference.addFocusListener(new EcouteurFocus(textFieldReference, placeholderRef));



        // Ajout des écouteurs d'événements aux boutons
        boutonRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer la référence de formation depuis le champ de texte
                int reference;

                if(textFieldReference.getText().trim().equals(placeholderRef)) {
                    JOptionPane.showMessageDialog(null, "Remplir le champ de référence d'abord !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    reference = Integer.parseInt(textFieldReference.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La référence doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                try {
                    // Appeler la méthode rechercherFormation
                    Formation formation = formationDAO.rechercherFormation(reference);

                    // Vérifier si la formation a été trouvée
                    if (formation != null) {
                        // Afficher les informations de la formation dans les champs de texte correspondants
                        textFieldTitre.setText(formation.getTitre());
                        textFieldDate.setText(formation.getDateF().toString()); // Conversion de Date en String
                        textFieldLieu.setText(formation.getLieu());
                        checkBoxCertification.setSelected(formation.getCertification());

                        // Activer les boutons modifier et supprimer
                        boutonModifier.setEnabled(true);
                        boutonSupprimer.setEnabled(true);

                        // Activer les champs de texte
                        textFieldTitre.setEnabled(true);
                        textFieldDate.setEnabled(true);
                        textFieldLieu.setEnabled(true);
                        checkBoxCertification.setEnabled(true);
                    } else {
                        // Réinitialiser les champs de texte
                        textFieldTitre.setText(placeholderTitre);
                        textFieldDate.setText(placeholderDate);
                        textFieldLieu.setText(placeholderLieu);
                        checkBoxCertification.setSelected(false);

                        // Désactiver les boutons modifier et supprimer
                        boutonModifier.setEnabled(false);
                        boutonSupprimer.setEnabled(false);

                        // Désactiver les champs de texte
                        textFieldTitre.setEnabled(false);
                        textFieldDate.setEnabled(false);
                        textFieldLieu.setEnabled(false);
                        checkBoxCertification.setEnabled(false);

                        // Afficher un message indiquant que la référence de formation n'a pas été trouvée
                        JOptionPane.showMessageDialog(null, "Référence de formation non trouvée", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    // Gérer les exceptions liées à la base de données
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la recherche de la formation", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        boutonModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer la référence de formation depuis le champ de texte
                int reference;

                if(textFieldReference.getText().trim().equals(placeholderRef)) {
                    JOptionPane.showMessageDialog(null, "Remplir le champ de référence d'abord !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    reference = Integer.parseInt(textFieldReference.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La référence doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Récupérer les informations de la formation à partir des champs de texte
                String titre = textFieldTitre.getText();
                String lieu = textFieldLieu.getText();
                Date date = null;

                if(textFieldDate.getText().equals(placeholderDate)) {
                    JOptionPane.showMessageDialog(null, "Remplir le champ de date d'abord !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = new Date(dateFormat.parse(textFieldDate.getText()).getTime());
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Format de date incorrect, utilisez yyyy-MM-dd", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean certification = checkBoxCertification.isSelected();


                try {
                    // Appeler la méthode modifierFormation
                    formationDAO.modifierFormation(reference, titre, lieu, date, certification);

                    // Afficher un message indiquant que la formation a été modifiée avec succès
                    JOptionPane.showMessageDialog(null, "Formation modifiée avec succès !");

                    // Réinitialiser les champs de texte et désactiver les boutons
                    textFieldReference.setText(placeholderRef);
                    textFieldTitre.setText(placeholderTitre);
                    textFieldDate.setText(placeholderDate);
                    textFieldLieu.setText(placeholderLieu);
                    checkBoxCertification.setSelected(false);
                    boutonModifier.setEnabled(false);
                    boutonSupprimer.setEnabled(false);
                    textFieldTitre.setEnabled(false);
                    textFieldDate.setEnabled(false);
                    textFieldLieu.setEnabled(false);
                    checkBoxCertification.setEnabled(false);
                } catch (SQLException ex) {
                    // Gérer les exceptions liées à la base de données
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la modification de la formation", "Erreur", JOptionPane.ERROR_MESSAGE);
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
                // Récupérer la référence de formation depuis le champ de texte
                int reference;

                if(textFieldReference.getText().trim().equals(placeholderRef)) {
                    JOptionPane.showMessageDialog(null, "Remplir le champ de référence d'abord !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    reference = Integer.parseInt(textFieldReference.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La référence doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Créer une instance de la classe FormationDAO pour utiliser la méthode supprimerFormation

                try {
                    // Demander une confirmation avant de supprimer
                    int choix = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette formation ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                    if (choix == JOptionPane.YES_OPTION) {
                        // Appeler la méthode supprimerFormation
                        formationDAO.supprimerFormation(reference);

                        // Afficher un message indiquant que la formation a été supprimée avec succès
                        JOptionPane.showMessageDialog(null, "Formation supprimée avec succès !");

                        // Réinitialiser les champs de texte et désactiver les boutons
                        textFieldReference.setText("");
                        textFieldTitre.setText(placeholderTitre);
                        textFieldDate.setText(placeholderDate);
                        textFieldLieu.setText(placeholderLieu);
                        checkBoxCertification.setSelected(false);
                        boutonModifier.setEnabled(false);
                        boutonSupprimer.setEnabled(false);
                        textFieldTitre.setEnabled(false);
                        textFieldDate.setEnabled(false);
                        textFieldLieu.setEnabled(false);
                        checkBoxCertification.setEnabled(false);
                    }
                } catch (SQLException ex) {
                    // Gérer les exceptions liées à la base de données
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la formation", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Création d'un panneau pour organiser les composants avec GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Ajout des composants au panneau
        panel.add(labelReference, gbc);
        gbc.gridx++;
        panel.add(textFieldReference, gbc);
        gbc.gridx++;
        panel.add(boutonRecherche, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelTitre, gbc);
        gbc.gridx++;
        panel.add(textFieldTitre, gbc);
        gbc.gridx++;
        panel.add(boutonModifier, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelLieu, gbc);
        gbc.gridx++;
        panel.add(textFieldLieu, gbc);
        gbc.gridx++;
        panel.add(boutonSupprimer, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelDate, gbc);
        gbc.gridx++;
        panel.add(textFieldDate, gbc);
        gbc.gridx++;
        panel.add(boutonAnnuler, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelCertification, gbc);
        gbc.gridx++;
        panel.add(checkBoxCertification, gbc);

        // Ajout du panneau à la fenêtre interne
        getContentPane().add(panel);

        setVisible(true); // Afficher la fenêtre
    }
}
