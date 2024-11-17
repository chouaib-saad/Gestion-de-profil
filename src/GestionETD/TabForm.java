package GestionETD;

import javax.swing.*;
import java.awt.*;

public class TabForm extends JPanel {
    private Profil profil;
    private Formulaire formulaire;

    public TabForm(Profil profil) {
        this.profil = profil;
        this.formulaire = new Formulaire(profil);
        this.setLayout(new BorderLayout());
        this.add(formulaire, BorderLayout.CENTER);
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Formulaire getFormulaire() {
        return formulaire;
    }

    public void setFormulaire(Formulaire formulaire) {
        this.formulaire = formulaire;
    }

    // Method to update the Formulaire component with new profile information
    public void updateProfil(Profil nouveauProfil) {
        this.profil = nouveauProfil;
        this.formulaire.updateProfil(nouveauProfil);
    }
}
