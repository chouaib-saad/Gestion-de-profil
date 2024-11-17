package Formation.Exercice1;

import java.sql.Connection;


public class Main {
    public static void main(String[] args) {


        System.out.printf("EXERCICE 1 ");
        Connection con = ConnectionTp4.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
        EtudiantDAOFormation ETDAO = new EtudiantDAOFormation(Config.URL , Config.USERNAME , Config.PASSWORD);
        ETDAO.EtudiantMoyenneSup10();
//        ETDAO.supprimerEtudiant(45678123);
        System.out.printf("EXERCICE 2 ");

    }
    }
