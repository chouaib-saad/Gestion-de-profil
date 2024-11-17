package Formation.GestionFormation.Helpers;

import java.util.Date;

public class Formation {
    int Id ;
    String Titre ;
    Date DateF ;
    String Lieu ;
    Boolean Certification ;

    public Formation(int Id, String Titre, Date DateF, String Lieu, Boolean Certification){
        this.Id = Id ;
        this.Titre = Titre ;
        this.DateF = DateF ;
        this.Lieu = Lieu ;
        this.Certification = Certification; ;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitre() {
        return Titre;
    }
    public void setTitre(String titre) {
        Titre = titre;
    }

    public java.sql.Date getDateF() {
        return (java.sql.Date) DateF;
    }
    public void setDateF(Date dateF) {
        DateF = dateF;
    }

    public String getLieu() {
        return Lieu;
    }
    public void setLieu(String lieu) {
        Lieu = lieu;
    }

    public Boolean getCertification() {
        return Certification;
    }
    public void setCertification(Boolean certification) {
        Certification = certification;
    }
}
