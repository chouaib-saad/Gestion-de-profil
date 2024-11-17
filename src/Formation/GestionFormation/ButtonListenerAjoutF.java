package Formation.GestionFormation;


import Formation.GestionFormation.DAO.FormationDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ButtonListenerAjoutF implements ActionListener {
    private JTextField textFieldRef;
    private JTextField textFieldTitre;
    private JTextField textFielddate;
    private JTextField textFieldlieu;
    private JCheckBox checkBoxCertification;
    private FormationDAO formationDAO;

    public ButtonListenerAjoutF(JTextField textFieldRef, JTextField textFieldTitre, JTextField textFielddate, JTextField textFieldlieu, JCheckBox checkBoxCertification) {
        this.textFieldRef = textFieldRef;
        this.textFieldTitre = textFieldTitre;
        this.textFielddate = textFielddate;
        this.textFieldlieu = textFieldlieu;
        this.checkBoxCertification = checkBoxCertification;
        this.formationDAO = new FormationDAO();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // Récupérer les données des champs de texte et de la case à cocher
        String refText = textFieldRef.getText().trim();
        String titreText = textFieldTitre.getText().trim();
        String dateText = textFielddate.getText().trim();
        String lieuText = textFieldlieu.getText().trim();
        boolean certification = checkBoxCertification.isSelected();


        if (refText.equals("Entrez la Reference") || titreText.equals("Entrez le Titre") || dateText.equals("AAAA/MM/JJ") ||   lieuText.equals("Entrez le lieu")) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs avant de procéder à la validation !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {

            Integer.parseInt(refText);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Le format de la reference est incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; // Sortir de la méthode si le format de la date est incorrect
        }




        // Vérifier si la date est au bon format
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(dateText);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Le format de la date est incorrect. Utilisez le format 'yyyy-MM-dd'.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; // Sortir de la méthode si le format de la date est incorrect
        }

        // Convertir la date en java.sql.Date
        Date date = null;
        try {
            java.util.Date utilDate = format.parse(dateText);
            date = new Date(utilDate.getTime());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        // Insérer les données dans la base de données
        int result = formationDAO.insertFormation(Integer.parseInt(refText), titreText, date, lieuText, certification);
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Formation ajoutée avec succès !");
            textFieldRef.setText("Entrez la Reference");
            textFieldTitre.setText("Entrez le Titre");
            textFielddate.setText("AAAA/MM/JJ");
            textFieldlieu.setText("Entrez le lieu");

        } else {
            JOptionPane.showMessageDialog(null, "La formation est déja existe !");
        }
    }



}
