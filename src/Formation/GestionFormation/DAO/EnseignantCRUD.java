package Formation.GestionFormation.DAO;

import Formation.GestionFormation.Helpers.DemandeData;
import Formation.GestionFormation.Helpers.Enseignant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface EnseignantCRUD {
    Enseignant rechercherEnseignant(int numCarte) throws SQLException;
    void modifierEnseignant(int numCarte, String nom, String prenom, String specialite, String grade) throws SQLException;
    void supprimerEnseignant(int numCarte) throws SQLException;
    void deleteDemandeENS(int formationId, int studentId) throws SQLException;
    Boolean ajouterDemandeENS(int idFormation, int idEnseignant) throws SQLException;
    int insertEnseignant(int id, String nom, String prenom, String specialite, String grade) throws SQLException;
    ResultSet getAllEnseignants() throws SQLException;
    List<DemandeData> getAllDemandesForEnseignats(int idEnseignant) throws SQLException;
    boolean demandeExists(String tableName,String person, int idFormation, int id) throws SQLException;
    int supprimerDemande(String tableName, String idColumnName, int id);
    int getFormationIdByTitre(String titre) throws SQLException;
    String[] getFormationTitles() throws SQLException;

}
