package GestionETD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame implements ActionListener {

    private JMenuBar newBar;
    private JMenu newTP;
    private JMenuItem menuItemTp1;
    private JMenuItem menuItemTp2;

    private JDesktopPane Desktop;


    Principal(){


        //ajouter une frame interne (container) pour l'interface creation profil
        Desktop = new JDesktopPane();
        this.add(Desktop);


        this.setTitle("TP JAVA");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //set Icon
        ImageIcon imageIcon = new ImageIcon("images/config.png");
        imageIcon.setDescription("icon de feunetre principal");
        this.setIconImage(imageIcon.getImage());



       newBar = new JMenuBar();
       newTP = new JMenu("TPs");
       newBar.add(newTP);
       menuItemTp1 = new JMenuItem("tp1");
       menuItemTp2 = new JMenuItem("tp2");
       newTP.add(menuItemTp1);
       newTP.add(menuItemTp2);
       this.setJMenuBar(newBar);





       //on click event
        menuItemTp1.addActionListener(this);
        menuItemTp2.addActionListener(this);

















        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == menuItemTp1){

            Gestion_Profil gp = new Gestion_Profil();
            Desktop.add(gp);
        }
        else if(e.getSource() == menuItemTp2){

            MaFenetre mf = new MaFenetre();
            Desktop.add(mf);

        }
    }
}
