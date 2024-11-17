package Formation.FormationV2;

import javax.swing.*;

public class DB_MainEtd {

    public static void main(String[] args) {


        //connection avec le base de données
        String username = database.Config.USERNAME;
        String password = database.Config.PASSWORD;
        String url = Config.URL;





        EtdDAO doo = new EtdDAO(url,username,password);





        //ajouts
        int a = doo.insertEtudiant(12345678,"chouaib","saad",13);
        int b = doo.insertEtudiant(78456123,"Mohammed","amine",10);
        int c = doo.insertEtudiant(45678123,"Alaa","aziz",15);

        //modification
        doo.modifierEtudiant(12345678,"chouaib","ben saad",14);


        //affichage de tous
//        doo.afficheAll();


        //supression
        doo.supprimerEtudiant(12345678);





        SwingUtilities.invokeLater(() -> {
            // Créer une fenêtre principale
//            JFrame frame = new JFrame("Gestion des étudiants");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(800, 600);

            // Ajouter Gestion_Etudiant à la fenêtre principale
//            GestionEtudiant gestionEtudiant = new GestionEtudiant();
//            frame.getContentPane().add(gestionEtudiant);
//
//            // Rendre la fenêtre principale visible
//            frame.setVisible(true);
        });



    }


}