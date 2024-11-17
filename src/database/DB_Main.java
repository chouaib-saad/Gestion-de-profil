package database;

import javax.swing.*;

public class DB_Main {

    public static void main(String[] args) {


        //connection avec le base de données
        String username = Config.USERNAME;
        String password = Config.PASSWORD;
        String url = Config.URL;





        EtudiantDAO doo = new EtudiantDAO(url,username,password);




        //ajouts
        int a = doo.insertEtudiant("chouaib","saad",12345678,13);
        int b = doo.insertEtudiant("Mohammed","amine",78456123,10);
        int c = doo.insertEtudiant("Alaa","aziz",45678123,15);

        //modification
        doo.modifierEtudiant("chouaib","ben saad",12345678,14);


        //affichage de tous
        doo.afficheAll();


        //supression
        doo.supprimerEtudiant(12345678);






        SwingUtilities.invokeLater(() -> {
            // Créer une fenêtre principale
            JFrame frame = new JFrame("Gestion des étudiants");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Ajouter Gestion_Etudiant à la fenêtre principale
            GestionEtudiant gestionEtudiant = new GestionEtudiant();
            frame.getContentPane().add(gestionEtudiant);

            // Rendre la fenêtre principale visible
            frame.setVisible(true);
        });



    }


}