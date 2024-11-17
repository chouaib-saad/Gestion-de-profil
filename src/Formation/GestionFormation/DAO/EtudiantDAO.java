package Formation.GestionFormation.DAO;

import Formation.GestionFormation.Helpers.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO implements EtudiantCRUD {
    Connection conn = null;

    public EtudiantDAO() {
        this.conn = MyConnection.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
    }

    @Override
    public List<DemandeData> getAllDemandesForEtudiant(int idEtudiant) throws SQLException {

        List<DemandeData> demandes = new ArrayList<>();
        // Requête SQL pour sélectionner toutes les demandes pour un étudiant donné
        String sql = "SELECT F.titre, F.lieu, F.dateF " +
                "FROM demandeetd D " +
                "INNER JOIN Formation F ON D.idFormation = F.idF " +
                "WHERE D.idEtudiant = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idEtudiant);
            try (ResultSet resultSet = statement.executeQuery()) {
                // Parcourir les résultats de la requête et créer des objets DemandeData
                while (resultSet.next()) {
                    String titreFormation = resultSet.getString("titre");
                    String lieuFormation = resultSet.getString("lieu");
                    String dateFormation = resultSet.getString("dateF");

                    // Créer un objet DemandeData avec les données récupérées
                    DemandeData demandeData = new DemandeData(titreFormation, lieuFormation, dateFormation);

                    // Ajouter la demande à la liste
                    demandes.add(demandeData);
                }
            }
        }
        return demandes;
    }

    @Override
    public ResultSet getAllEtudiants() throws SQLException {
        String query = "SELECT * FROM etudiant";
        PreparedStatement statement = conn.prepareStatement(query);
        return statement.executeQuery();
    }

    @Override
    public int insertEtudiant(int id, String nom, String prenom, String filiere, int niveau, int groupe) {

        PreparedStatement st = null;
        try {
            if (conn != null) {
                String req = "INSERT INTO etudiant (id, nom, prenom, filiere, niveau, groupe) VALUES (?, ?, ?, ?, ?, ?)";
                st = conn.prepareStatement(req);
                st.setInt(1, id);
                st.setString(2, nom);
                st.setString(3, prenom);
                st.setString(4, filiere);
                st.setInt(5, niveau);
                st.setInt(6, groupe);

                int rowsAffected = st.executeUpdate();
                System.out.println("Nombre de lignes affectées : " + rowsAffected);
                return rowsAffected;
            } else {
                System.out.println("La connexion à la base de données est nulle.");
                return 0;
            }
        } catch (SQLException ex) {

            if (ex.getErrorCode() == 1062) { // MySQL error code for duplicate entry
                System.out.println("Erreur lors de l'ajout : Le CIN " + id + " existe déjà.");
                return -1;}
            else {
                System.out.println("Erreur d'exécution de la requête : " + ex.getMessage());
                return 0;
            }
        }

    }

    @Override
    public Boolean ajouterDemandeETD(int idFormation, int idEtudiant) throws SQLException {

        // Check if the record already exists
        if (demandeExists("demandeetd","idEtudiant",idFormation, idEtudiant)) {
            System.out.println("Demande already exists for this user and formation.");
            return false; // Exit without inserting a duplicate
        }
        else {
            // Insert the new demand if it doesn't already exist
            String query = "INSERT INTO demandeetd (idFormation, idEtudiant) VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, idFormation);
                statement.setInt(2, idEtudiant);
                statement.executeUpdate();
                System.out.println("New demand added for formation ID: " + idFormation + ", student ID: " + idEtudiant);
            }
            return true;
        }
    }



    @Override
    public void deleteDemandeETD(int formationId, int studentId) throws SQLException {


        // Write your SQL query to delete the demand for the specific student
        String query = "DELETE FROM demandeetd WHERE idFormation = ? AND idEtudiant  = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, formationId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
        }

    }

    @Override
    public void supprimerEtudiant(int numCarte) throws SQLException {
        // Delete related demands first
        supprimerDemande("demandeetd", "idEtudiant", numCarte);

        // Then delete the student
        String sql = "DELETE FROM Etudiant WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, numCarte);
            statement.executeUpdate();
        }
    }

    @Override
    public void modifierEtudiant(int numCarte, String nom, String prenom, String filiere, int niveau, int groupe) throws SQLException {

        String sql = "UPDATE Etudiant SET nom = ?, prenom = ?, filiere = ?, niveau = ?, groupe = ? WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, nom);
        statement.setString(2, prenom);
        statement.setString(3, filiere);
        statement.setInt(4, niveau);
        statement.setInt(5, groupe);
        statement.setInt(6, numCarte);
        statement.executeUpdate();
        statement.close();

    }

    @Override
    public Etudiant rechercherEtudiant(int NumCarte) throws SQLException {

        String sql = "SELECT * FROM Etudiant WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, NumCarte);
        ResultSet resultSet = statement.executeQuery();

        Etudiant etudiant = null;

        if (resultSet.next()) {
            // Si une ligne est trouvée, créez un objet Etudiant avec les informations récupérées
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String filiere = resultSet.getString("filiere");
            int niveau = resultSet.getInt("niveau");
            int groupe = resultSet.getInt("groupe");

            etudiant = new Etudiant(id,nom,prenom, filiere, niveau, groupe);
        }

        resultSet.close();
        statement.close();

        return etudiant;


    }

    @Override
    public boolean demandeExists(String tableName, String person, int idFormation, int id) throws SQLException {

        String query = "SELECT COUNT(*) AS count FROM " + tableName + " WHERE idFormation = ? AND "+person+" = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idFormation);
            statement.setInt(2, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    System.out.println(count);
                    return count > 0; // Returns true if a record exists, false otherwise
                }
            }
        }
        return false; // Return false in case of any error or if no record is found

    }

    @Override
    public int supprimerDemande(String tableName, String idColumnName, int id) {
        int rowsAffected = 0;
        String sql = "DELETE FROM " + tableName + " WHERE " + idColumnName + " = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id); // Définir la valeur du paramètre
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsAffected;
    }


    @Override
    public int getFormationIdByTitre(String titre) throws SQLException {

        String query = "SELECT idF FROM Formation WHERE titre = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, titre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("idF");
                }
            }
        }
        throw new SQLException("Formation non trouvée pour le titre: " + titre);
    }


    @Override
    public String[] getFormationTitles() throws SQLException {

        String sql = "SELECT titre FROM Formation";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        // Count the number of rows returned
        int rowCount = 0;
        if (resultSet.last()) {
            rowCount = resultSet.getRow();
            resultSet.beforeFirst(); // Move cursor back to the start
        }

        // Create an array to store the titles
        String[] titles = new String[rowCount];

        // Iterate through the result set and store titles in the array
        int index = 0;
        while (resultSet.next()) {
            titles[index++] = resultSet.getString("titre");
        }

        resultSet.close();
        statement.close();

        return titles;
    }

}
