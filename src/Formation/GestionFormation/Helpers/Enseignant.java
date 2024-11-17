package Formation.GestionFormation.Helpers;

public class Enseignant extends Personne {
    private String specialite;
    private String grade;

    public Enseignant(int id, String nom, String prenom,String specialite,String grade) {
        super(id, nom, prenom);
        this.specialite = specialite;
        this.grade = grade;
    }


    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
