package Formation.GestionFormation.DAO;

import Formation.GestionFormation.Helpers.*;
import java.sql.*;

public class FormationDAO implements FormationCRUD {
    Connection conn = null;


    public FormationDAO() {
        this.conn = MyConnection.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
    }

    @Override
    public int insertFormation(int id, String titre, Date date, String lieu, boolean certification){
        PreparedStatement st = null;
        try {
            if (conn != null) {
                String req = "INSERT INTO formation (idF, titre, dateF, lieu, certif) VALUES (?, ?, ?, ?, ?)";
                st = conn.prepareStatement(req);
                st.setInt(1, id);
                st.setString(2, titre);
                st.setDate(3, new Date(date.getTime())); // Conversion java.util.Date en java.sql.Date
                st.setString(4, lieu);
                st.setBoolean(5, certification);

                int rowsAffected = st.executeUpdate();
                System.out.println("Nombre de lignes affectées : " + rowsAffected);
                return rowsAffected;
            } else {
                System.out.println("La connexion à la base de données est nulle.");
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Erreur d'exécution de la requête : " + ex.getMessage());
            return 0;
        }

    }

    @Override
    public void supprimerFormation(int reference) throws SQLException {
        String sql = "DELETE FROM Formation WHERE idF = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, reference);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public int supprimerDemande(String tableName, String idColumnName, int id) throws SQLException {
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
    public void modifierFormation(int reference, String titre, String lieu, Date date, boolean certification) throws SQLException {
        String sql = "UPDATE Formation SET titre = ?, lieu = ?, dateF = ?, certif = ? WHERE idF = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, titre);
        statement.setString(2, lieu);
        statement.setDate(3, date);
        statement.setBoolean(4, certification);
        statement.setInt(5, reference);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Formation rechercherFormation(int reference) throws SQLException {

        String sql = "SELECT * FROM Formation WHERE idF = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, reference);
        ResultSet resultSet = statement.executeQuery();

        Formation formation = null;

        if (resultSet.next()) {
            // Si une ligne est trouvée, créez un objet Formation avec les informations récupérées
            int id = resultSet.getInt("idF");
            String titre = resultSet.getString("titre");
            Date dateF = resultSet.getDate("dateF");
            String lieu = resultSet.getString("lieu");
            boolean certification = resultSet.getBoolean("certif");

            formation = new Formation(id, titre, dateF, lieu, certification);
        }

        resultSet.close();
        statement.close();
        return formation;

    }

    @Override
    public ResultSet getAllFormations() throws SQLException {

        String query = "SELECT * FROM Formation";
        PreparedStatement statement = conn.prepareStatement(query);
        return statement.executeQuery();

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
}
