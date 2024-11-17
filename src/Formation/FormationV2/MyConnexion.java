package Formation.FormationV2;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnexion {

    public static Connection getConnection(String url,String username,String password){

        //chargement Driver
        String nomDriver = "com.mysql.jdbc.Driver";

        try {
            Class.forName(nomDriver);
            System.out.println("Driver chargé...");

        } catch (ClassNotFoundException e) {

            System.out.println("erreur Driver : " + e.getMessage());

        }




        Connection connection = null;


        try {
            connection = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Connected..");
            return connection;
        } catch (SQLException e) {
            System.out.println("Erreur de connection : " + e.getMessage());
            return null;
        }




    }

}
