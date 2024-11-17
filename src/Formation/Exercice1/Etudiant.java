package Formation.Exercice1;

public class Etudiant {
    String nom;
    String prenom;
    Float moyenne;

    public Etudiant(String nom, String prenom, Float moyenne) {
        this.nom = nom;
        this.prenom = prenom;
        this.moyenne = moyenne;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", moyenne=" + moyenne +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }
}