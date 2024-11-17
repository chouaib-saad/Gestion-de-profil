package Formation.FormationV2;

import Formation.FormationV2.EtdDaoCrud;
import database.EtudiantDAOCRUD;

import java.sql.*;

public class EtdDAO implements EtdDaoCrud {


    private Connection connection;
    //chargement Driver
    String nomDriver = "com.mysql.jdbc.Driver";

    public EtdDAO(String url, String username, String password) {
        try {

            try {
                Class.forName(nomDriver);
                System.out.println("Driver chargé...");

            } catch (ClassNotFoundException e) {

                System.out.println("La classe "+nomDriver+" n'a pas été trouvée");

            }
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }


    @Override
    public int insertEtudiant(int netd, String nom, String prénom, double moyenne) {
        String req = "INSERT INTO Etudiant (netd,nom, prénom, moyenne) VALUES (?, ?, ?,?)";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, netd);
            ps.setString(2, nom);
            ps.setString(3, prénom);
            ps.setDouble(4, moyenne);

            System.out.println("ajout avec succes ! ");


            return ps.executeUpdate(); // Returns the number of rows affected
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // MySQL error code for duplicate entry
                System.out.println("Erreur lors de l'ajout : Le NETD " + netd + " existe déjà.");
                return 1062;
            }else {
                System.out.println("Erreur lors de l'ajout : " + e.getMessage());
                return 0; // Return 0 to indicate failure
            }
        }
    }




    @Override
    public int supprimerEtudiant(int netd) {
        int rowsAffected = 0;
        String sql = "DELETE FROM Etudiant WHERE netd = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, netd);

            rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Suppression avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }

        return rowsAffected;
    }

    @Override
    public int modifierEtudiant(int netd, String nom, String prenom, double moyenne) {
        int rowsAffected = 0;
        String sql = "UPDATE Etudiant SET nom = ?, prénom = ?, moyenne = ? WHERE netd = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, netd);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setDouble(4, moyenne);

            rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Modification avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }

        return rowsAffected;
    }

    @Override
    public void afficheAll() {
        try (Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM Etudiant";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int netd = resultSet.getInt("netd");
                String nom = resultSet.getString("nom");
                String prénom = resultSet.getString("prénom");
                double moyenne = resultSet.getDouble("moyenne");

                System.out.println("Nom: " + nom + " | Prénom: " + prénom + " | netd: " + netd + " | Moyenne: " + moyenne);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage : " + e.getMessage());
        }
    }



    //a completer ...


    ResultSet selection(String req) {
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(req);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection : " + e.getMessage());
        }
        return rs;
    }

    void afficheResultSet(ResultSet rs) {
        try {
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prénom");
                int netd = rs.getInt("netd");
                double moyenne = rs.getDouble("moyenne");

                System.out.println("Nom: " + nom + " | Prénom: " + prenom + " | NETD: " + netd + " | Moyenne: " + moyenne);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage du ResultSet : " + e.getMessage());
        }
    }




//    void afficheResultSet(ResultSet rs) {
//        try {
//            ResultSetMetaData metaData = rs.getMetaData();
//            int columnCount = metaData.getColumnCount();
//
//            while (rs.next()) {
//                for (int i = 1; i <= columnCount; i++) {
//                    Object value = rs.getObject(i);
//                    System.out.print(value + " | ");
//                }
//                System.out.println(); // Nouvelle ligne pour chaque ligne de résultat
//            }
//        } catch (SQLException e) {
//            System.out.println("Erreur lors de l'affichage du ResultSet : " + e.getMessage());
//        }
//    }



    void supprimer(int netd) {
        String req = "DELETE FROM Etudiant WHERE netd = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, netd);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Suppression avec succès");
            } else {
                System.out.println("Aucun enregistrement trouvé avec le NETD : " + netd);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }



    void modifier(int netd, String nouveauNom, String nouveauPrenom, double nouvelleMoyenne) {
        String req = "UPDATE Etudiant SET nom = ?, prénom = ?, moyenne = ? WHERE netd = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, nouveauNom);
            preparedStatement.setString(2, nouveauPrenom);
            preparedStatement.setDouble(3, nouvelleMoyenne);
            preparedStatement.setInt(4, netd);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Modification avec succès");
            } else {
                System.out.println("Aucun enregistrement trouvé avec le NETD : " + netd);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }





    //methode supprimer tous de la bdd
    public int supprimerTous() {
        int rowsAffected = 0;
        String sql = "DELETE FROM Etudiant";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Tous les étudiants ont été supprimés avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de tous les étudiants : " + e.getMessage());
        }

        return rowsAffected;
    }



}
