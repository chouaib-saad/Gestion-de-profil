package Formation.GestionFormation.DAO;

import Formation.GestionFormation.Helpers.DemandeData;
import Formation.GestionFormation.Helpers.Etudiant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface EtudiantCRUD {
    List<DemandeData> getAllDemandesForEtudiant(int idEtudiant) throws SQLException;
    ResultSet getAllEtudiants() throws SQLException;
    int insertEtudiant(int id, String nom, String prenom, String filiere, int niveau, int groupe) throws SQLException;
    Boolean ajouterDemandeETD(int idFormation, int idEtudiant) throws SQLException;
    void deleteDemandeETD(int formationId, int studentId) throws SQLException;
    void supprimerEtudiant(int numCarte) throws SQLException;
    void modifierEtudiant(int numCarte, String nom, String prenom, String filiere, int niveau, int groupe) throws SQLException;
    Etudiant rechercherEtudiant(int NumCarte) throws SQLException;
    boolean demandeExists(String tableName,String person, int idFormation, int id) throws SQLException;
    int supprimerDemande(String tableName, String idColumnName, int id);
    int getFormationIdByTitre(String titre) throws SQLException;
    String[] getFormationTitles() throws SQLException;

}
