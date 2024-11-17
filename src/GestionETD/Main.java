package GestionETD;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

//        MaFenetre mf = new MaFenetre();
//          Gestion_Profil gp = new Gestion_Profil();


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                Principal p = new Principal();

            }
        });




    }
}