package Formation.FormationV2;

public interface EtdDaoCrud {


    public int insertEtudiant(int netd, String nom, String prenom, double moyenne);

    public int supprimerEtudiant(int netd);

    public int modifierEtudiant(int  netd, String nom, String prenom, double moyenne);

    public void afficheAll();

}
