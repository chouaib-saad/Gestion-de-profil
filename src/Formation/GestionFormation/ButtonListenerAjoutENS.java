package Formation.GestionFormation;

import Formation.GestionFormation.DAO.EnseignantDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListenerAjoutENS implements ActionListener {
    private JTextField textFieldNumCarte;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldSpecialite;
    private JComboBox<String> comboBoxGrade;
    private EnseignantDAO enseignantDAO;

    private String placeholderNumCarte = "Entrez le numéro de carte";
    private String placeholderNom = "Entrez le nom";
    private String placeholderPrenom = "Entrez le prénom";
    private String placeholderSpecialite = "Entrez la spécialité";

    public ButtonListenerAjoutENS(JTextField textFieldNumCarte, JTextField textFieldNom, JTextField textFieldPrenom,
                                  JTextField textFieldSpecialite, JComboBox<String> comboBoxGrade) {
        this.textFieldNumCarte = textFieldNumCarte;
        this.textFieldNom = textFieldNom;
        this.textFieldPrenom = textFieldPrenom;
        this.textFieldSpecialite = textFieldSpecialite;
        this.comboBoxGrade = comboBoxGrade;
        this.enseignantDAO = new EnseignantDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Récupérer les données des champs de texte
        String numCarteText = textFieldNumCarte.getText().trim();
        String nom = textFieldNom.getText().trim();
        String prenom = textFieldPrenom.getText().trim();
        String specialite = textFieldSpecialite.getText().trim();
        String grade = comboBoxGrade.getSelectedItem().toString(); // Récupérer le grade sélectionné

        // Vérifier si tous les champs sont remplis
        if (numCarteText.equals(placeholderNumCarte) || nom.equals(placeholderNom) || prenom.equals(placeholderPrenom) || specialite.equals(placeholderSpecialite)) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs avant de procéder à la validation !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérifier si le numéro de carte est un entier
        try {
            Integer.parseInt(numCarteText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Le format du numéro de carte est incorrect. Veuillez entrer un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insérer les données dans la base de données
        int result = enseignantDAO.insertEnseignant(Integer.parseInt(numCarteText), nom, prenom, specialite, grade); // Ajouter le grade comme paramètre
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Enseignant ajouté avec succès !");
            textFieldNumCarte.setText("Entrez le numéro de carte");
            textFieldNom.setText("Entrez le nom");
            textFieldPrenom.setText("Entrez le prénom");
            textFieldSpecialite.setText("Entrez la spécialité");
            comboBoxGrade.setSelectedIndex(0); // Réinitialiser la sélection du grade
        } else if(result == -1) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout : l'enseignant existe déjà.");
        }
        else {
            JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de l'ajout de l'enseignant.");
        }
    }
}
