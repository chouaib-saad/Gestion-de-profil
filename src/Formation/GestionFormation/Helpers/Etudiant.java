package Formation.GestionFormation.Helpers;

public class Etudiant extends Personne {

    String filiere;
    private int niveau;
    private int groupe;

    public Etudiant(int id, String nom, String prenom, String filiere, int niveau, int groupe) {
        super(id, nom, prenom);
        this.filiere = filiere;
        this.niveau = niveau;
        this.groupe = groupe;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getGroupe() {
        return groupe;
    }

    public void setGroupe(int groupe) {
        this.groupe = groupe;
    }


}

