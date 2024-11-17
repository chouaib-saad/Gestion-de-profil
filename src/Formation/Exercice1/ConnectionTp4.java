package Formation.Exercice1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTp4 {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public static Connection getConnection(String url, String username, String password) {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connexion à la base de données...");

            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected...");

            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur driver" + e.getMessage());
        } catch (SQLException ex) {
            System.out.println("Erreur connexion " + ex.getMessage());
        }
        return null;
    }
}