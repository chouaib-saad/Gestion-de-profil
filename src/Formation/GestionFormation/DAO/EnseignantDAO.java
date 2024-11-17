package Formation.GestionFormation.DAO;

import Formation.GestionFormation.Helpers.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDAO implements EnseignantCRUD {
    Connection conn = null;

    public EnseignantDAO() {
        this.conn = MyConnection.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
    }

    @Override
    public Enseignant rechercherEnseignant(int numCarte) throws SQLException {
        String sql = "SELECT * FROM Enseignant WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, numCarte);
        ResultSet resultSet = statement.executeQuery();

        Enseignant enseignant = null;

        if (resultSet.next()) {
            // Si une ligne est trouvée, créez un objet Enseignant avec les informations récupérées
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String specialite = resultSet.getString("specialite");
            String grade = resultSet.getString("grade");

            enseignant = new Enseignant(id, nom, prenom, specialite, grade);
        }

        resultSet.close();
        statement.close();

        return enseignant;    }

    @Override
    public void modifierEnseignant(int numCarte, String nom, String prenom, String specialite, String grade) throws SQLException {
        String sql = "UPDATE enseignant SET nom = ?, prenom = ?, specialite = ?, grade = ? WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, nom);
        statement.setString(2, prenom);
        statement.setString(3, specialite);
        statement.setString(4, grade);
        statement.setInt(5, numCarte);
        statement.executeUpdate();
        statement.close();    }

    @Override
    public void supprimerEnseignant(int numCarte) throws SQLException {
        // Delete related demands first
        supprimerDemande("demandeens", "idEnseignant", numCarte);

        // Then delete the teacher
        String sql = "DELETE FROM enseignant WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, numCarte);
            statement.executeUpdate();
        }    }

    @Override
    public void deleteDemandeENS(int formationId, int studentId) throws SQLException {
        // Write your SQL query to delete the demand for the specific student
        String query = "DELETE FROM demandeens WHERE idFormation = ? AND idEnseignant  = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, formationId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
        }    }

    @Override
    public Boolean ajouterDemandeENS(int idFormation, int idEnseignant) throws SQLException {
        // Check if the record already exists
        if (demandeExists("demandeens","idEnseignant",idFormation, idEnseignant)) {
            System.out.println("Demande already exists for this formation and teacher.");
            return false; // Exit without inserting a duplicate
        }
        else {
            // Insert the new demand if it doesn't already exist
            String query = "INSERT INTO demandeens (idFormation, idEnseignant) VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, idFormation);
                statement.setInt(2, idEnseignant);
                statement.executeUpdate();
            }
            return true;
        }    }

    @Override
    public int insertEnseignant(int id, String nom, String prenom, String specialite, String grade) {
        PreparedStatement st = null;
        try {
            if (conn != null) {
                String req = "INSERT INTO enseignant (id, nom, prenom, specialite, grade) VALUES (?, ?, ?, ?, ?)";
                st = conn.prepareStatement(req);
                st.setInt(1, id);
                st.setString(2, nom);
                st.setString(3, prenom);
                st.setString(4, specialite);
                st.setString(5, grade); // Ajout du grade

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
                return -1;
            } else {
                System.out.println("Erreur d'exécution de la requête : " + ex.getMessage());
                return 0;
            }
        }    }

    @Override
    public ResultSet getAllEnseignants() throws SQLException {
        String query = "SELECT * FROM enseignant";
        PreparedStatement statement = conn.prepareStatement(query);
        return statement.executeQuery();    }

    @Override
    public List<DemandeData> getAllDemandesForEnseignats(int idEnseignant) throws SQLException {
        List<DemandeData> demandes = new ArrayList<>();

        // SQL query to select all demands for a given teacher
        String sql = "SELECT F.titre, F.lieu, F.dateF " +
                "FROM demandeens D " +
                "INNER JOIN Formation F ON D.idFormation = F.idF " +
                "WHERE D.idEnseignant = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idEnseignant);
            try (ResultSet resultSet = statement.executeQuery()) {
                // Iterate through the results and create DemandeData objects
                while (resultSet.next()) {
                    String titreFormation = resultSet.getString("titre");
                    String lieuFormation = resultSet.getString("lieu");
                    String dateFormation = resultSet.getString("dateF");

                    // Create a DemandeData object with the retrieved data
                    DemandeData demandeData = new DemandeData(titreFormation, lieuFormation, dateFormation);

                    // Add the demand to the list
                    demandes.add(demandeData);
                }
            }
        }
        return demandes;    }

    @Override
    public boolean demandeExists(String tableName, String person, int idFormation, int id) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM " + tableName + " WHERE idFormation = ? AND " + person + " = ?";
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
            return rowsAffected;    }


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
