package database;

public interface EtudiantDAOCRUD {


    public int insertEtudiant(String nom, String prenom, int cin, double moyenne);

    public int supprimerEtudiant(int cin);

    public int modifierEtudiant(String nom, String prenom, int cin, double moyenne);

    public void afficheAll();

}
