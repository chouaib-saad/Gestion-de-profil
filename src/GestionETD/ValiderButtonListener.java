package GestionETD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ValiderButtonListener implements ActionListener {
    private Gestion_Profil gestion_profil;

    public ValiderButtonListener(Gestion_Profil gestion_profil) {
        this.gestion_profil = gestion_profil;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nom = gestion_profil.getTextFieldNom().getText();
        String prenom = gestion_profil.getTextFieldPrenom().getText();
        String pseudo = gestion_profil.getTextFieldPseudo().getText();

        if (nom.isEmpty() || prenom.isEmpty() || pseudo.isEmpty()) {
            JOptionPane.showMessageDialog(gestion_profil, "Veuillez remplir tous les champs avant de procéder à la validation!", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean pseudoExiste = false;
            for (Profil profil : gestion_profil.getData()) {
                if (profil.getPseudo().equals(pseudo)) {
                    pseudoExiste = true;
                    break;
                }
            }

            boolean remplireForm;
            if(nom.equals("Saisir le nom") || prenom.equals("Saisir le prenom")|| pseudo.equals("Saisir le pseudo")){
                remplireForm = false;

            }else {

                remplireForm = true;

            }


            if (remplireForm) {


                if (!pseudoExiste) {
                    // Ajouter un objet profil dans la liste
                    gestion_profil.getData().add(new Profil(nom, prenom, pseudo));
                    // Ajouter le pseudo à votre modèle
                    gestion_profil.getModel().addElement(pseudo);
                } else {
                    JOptionPane.showMessageDialog(gestion_profil, "Le pseudo existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            }else{

                JOptionPane.showMessageDialog(gestion_profil, "Veuillez compléter le formulaire.", "Champs incomplets", JOptionPane.ERROR_MESSAGE);

            }
        }
    }
}
