package Formation.GestionFormation;

import Formation.GestionFormation.DAO.EtudiantDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListenerAjoutETD implements ActionListener {
    private JTextField textFieldNumCarte;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JComboBox<String> comboBoxFiliere;
    private JComboBox<Integer> comboBoxNiveau;
    private JComboBox<Integer> comboBoxGroupe;
    private EtudiantDAO etudiantDAO;


    private String placeholderNumCarte = "Entrez le numéro de carte";
    private String placeholderNom = "Entrez le nom";
    private String placeholderPrenom = "Entrez le prénom";

    public ButtonListenerAjoutETD(JTextField textFieldNumCarte, JTextField textFieldNom, JTextField textFieldPrenom,
                                  JComboBox<String> comboBoxFiliere, JComboBox<Integer> comboBoxNiveau, JComboBox<Integer> comboBoxGroupe) {
        this.textFieldNumCarte = textFieldNumCarte;
        this.textFieldNom = textFieldNom;
        this.textFieldPrenom = textFieldPrenom;
        this.comboBoxFiliere = comboBoxFiliere;
        this.comboBoxNiveau = comboBoxNiveau;
        this.comboBoxGroupe = comboBoxGroupe;
        this.etudiantDAO = new EtudiantDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Récupérer les données des champs de texte et des combobox
        String numCarteText = textFieldNumCarte.getText().trim();
        String nom = textFieldNom.getText().trim();
        String prenom = textFieldPrenom.getText().trim();
        String filiere = comboBoxFiliere.getSelectedItem().toString();
        int niveau = (int) comboBoxNiveau.getSelectedItem();
        int groupe = (int) comboBoxGroupe.getSelectedItem();

        // Vérifier si tous les champs sont remplis
        if (numCarteText.equals(placeholderNumCarte) || nom.equals(placeholderNom) || prenom.equals(placeholderPrenom)) {
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
        int result = etudiantDAO.insertEtudiant(Integer.parseInt(numCarteText), nom, prenom, filiere, niveau, groupe);
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Étudiant ajouté avec succès !");
            textFieldNumCarte.setText("Entrez le numéro de carte");
            textFieldNom.setText("Entrez le nom");
            textFieldPrenom.setText("Entrez le prénom");
        } else if(result == -1) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout : l'etudiant  existe déjà.");
        }
        else {
            JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de l'ajout de l'étudiant.");
        }
    }
}
