package Formation.GestionFormation.DAO;

import Formation.GestionFormation.Helpers.Formation;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface FormationCRUD {
    int insertFormation(int id, String titre, Date date, String lieu, boolean certification) throws SQLException;
    void supprimerFormation(int reference) throws SQLException;
    int supprimerDemande(String tableName, String idColumnName, int id) throws SQLException;
    void modifierFormation(int reference, String titre, String lieu, Date date, boolean certification) throws SQLException;
    Formation rechercherFormation(int reference) throws SQLException;
    ResultSet getAllFormations() throws SQLException;
    String[] getFormationTitles() throws SQLException;
    int getFormationIdByTitre(String titre) throws SQLException;
}
