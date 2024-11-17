package Formation.GestionFormation.Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfacePrincipal extends JFrame implements ActionListener {

    JMenuBar menuBar;
    JMenu menuFormation, menuEtudiant, menuEnseignant;
    JMenuItem itemAjoutFormation, itemRechercheFormation, itemAfficheFormation,
            itemAjoutEtud, itemRechercheEtud, itemAfficheEtud,
            itemAjoutEnse, itemRechercheEnse, itemAfficheEnse;

    public InterfacePrincipal() {
        setTitle("Gestion des formations");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        menuBar = new JMenuBar();
        menuFormation = new JMenu("Formation");
        menuEtudiant = new JMenu("Etudiant");
        menuEnseignant = new JMenu("Enseignant");

        itemAjoutFormation = new JMenuItem("Ajouter");
        itemRechercheFormation = new JMenuItem("Rechercher");
        itemAfficheFormation = new JMenuItem("Afficher");

        itemAjoutEtud = new JMenuItem("Ajouter");
        itemRechercheEtud = new JMenuItem("Rechercher");
        itemAfficheEtud = new JMenuItem("Afficher");

        itemAjoutEnse = new JMenuItem("Ajouter");
        itemRechercheEnse = new JMenuItem("Rechercher");
        itemAfficheEnse = new JMenuItem("Afficher");


        //make an action listner for each menu
        itemAjoutFormation.addActionListener(this);
        itemRechercheFormation.addActionListener(this);
        itemAfficheFormation.addActionListener(this);


        itemAjoutEtud.addActionListener(this);
        itemRechercheEtud.addActionListener(this);
        itemAfficheEtud.addActionListener(this);

        itemAjoutEnse.addActionListener(this);
        itemRechercheEnse.addActionListener(this);
        itemAfficheEnse.addActionListener(this);



        //attacher chaque menu a la menu bar
        menuBar.add(menuFormation);
        menuBar.add(menuEtudiant);
        menuBar.add(menuEnseignant);
        setJMenuBar(menuBar);



        //ajouter les items au chaque menu
        menuFormation.add(itemAjoutFormation);
        menuFormation.add(itemRechercheFormation);
        menuFormation.add(itemAfficheFormation);

        menuEtudiant.add(itemAjoutEtud);
        menuEtudiant.add(itemRechercheEtud);
        menuEtudiant.add(itemAfficheEtud);

        menuEnseignant.add(itemAjoutEnse);
        menuEnseignant.add(itemRechercheEnse);
        menuEnseignant.add(itemAfficheEnse);



        //ajout la feunetre par defaut (initialement ouverte)
        ajouterFenetreInterne(new IHMAjoutF());
        setVisible(true);
    }


    //initialisation de la current
    private JInternalFrame currentInternalFrame = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        // Close the currently displayed internal frame, if any
        if (currentInternalFrame != null) {
            currentInternalFrame.dispose();
        }

        if (e.getSource() == itemAjoutFormation) {
            IHMAjoutF ihmAjoutF = new IHMAjoutF();
            ajouterFenetreInterne(ihmAjoutF);
        } else if (e.getSource() == itemRechercheFormation) {
            IHMRechF ihmRechF = new IHMRechF();
            ajouterFenetreInterne(ihmRechF);
        } else if (e.getSource() == itemAfficheFormation) {
            IHMAffF ihmAffF = new IHMAffF();
            ajouterFenetreInterne(ihmAffF);
        }
        else if (e.getSource() == itemAjoutEtud) {
            IHMAjoutETD ihmAjoutETD = new IHMAjoutETD();
            ajouterFenetreInterne(ihmAjoutETD);
        }
        else if (e.getSource() == itemRechercheEtud) {
            IHMRechETD ihmRechETD = new IHMRechETD();
            ajouterFenetreInterne(ihmRechETD);

            //still have a lot of problemss..
            //not implementes yet..

        }
        else if (e.getSource() == itemAfficheEtud) {
            IHMAffETD ihmAffETD = new IHMAffETD();
            ajouterFenetreInterne(ihmAffETD);
        }
        else if (e.getSource() == itemAjoutEnse) {
            IHMAjoutENS ihmAjoutENS = new IHMAjoutENS();
            ajouterFenetreInterne(ihmAjoutENS);
        }

        else if (e.getSource() == itemRechercheEnse) {

            IHMRechENS ihmRechENS = new IHMRechENS();
            ajouterFenetreInterne(ihmRechENS);

        } else if (e.getSource() == itemAfficheEnse) {
            IHMAffENS ihmAffENS = new IHMAffENS();
            ajouterFenetreInterne(ihmAffENS);
        }




        // Add conditions for other menu items here
    }

    private void ajouterFenetreInterne(JInternalFrame internalFrame) {
        getContentPane().removeAll(); // Remove any existing internal frame
        getContentPane().add(internalFrame);
        internalFrame.setVisible(true);
        currentInternalFrame = internalFrame;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfacePrincipal::new);
    }
}
