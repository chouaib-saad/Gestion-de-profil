package Formation.Exercice1;

public interface EtudiantDAOCRUDFormation {
    public int insertEtudiant(String nom, String prenom, int cin, double moyenne);

    public void afficheAll(String req);

    public int supprimerEtudiant(int cin);

    public int modifierEtudiant(String nom, String prenom, Integer cin, Double moyenne);
    public void EtudiantMoyenneSup10();

}

