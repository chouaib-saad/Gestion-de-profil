package Formation.GestionFormation.Helpers;

public class DemandeData {
    private String titreFormation;
    private String lieuFormation;
    private String dateFormation;

    public DemandeData(String titreFormation, String lieuFormation, String dateFormation) {
        this.titreFormation = titreFormation;
        this.lieuFormation = lieuFormation;
        this.dateFormation = dateFormation;
    }

    public String getTitreFormation() {
        return titreFormation;
    }

    public String getLieuFormation() {
        return lieuFormation;
    }

    public String getDateFormation() {
        return dateFormation;
    }
}
