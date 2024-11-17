package Formation.GestionFormation.Helpers;

public class Demande {
    private int id;
    private int idFormation;
    private int idEtudiant;

    public Demande(int id, int idFormation, int idPersonne) {
        this.id = id;
        this.idFormation = idFormation;
        this.idEtudiant = idPersonne;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }
}
