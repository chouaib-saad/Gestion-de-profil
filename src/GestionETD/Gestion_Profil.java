package GestionETD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Gestion_Profil extends JInternalFrame {



    private DefaultListModel model;
    private JList jl;
    private JTabbedPane jtp;


    //instance pour l'instance formulaire
    private Formulaire formulaire;


    ArrayList<Profil> data = new ArrayList<Profil>();
    //liste de tabs pour TabForm
    private ArrayList<TabForm> onglets = new ArrayList<>();


    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldPseudo;



    Gestion_Profil() {
        this.setTitle("Gestion Profil");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setClosable(true);
        this.setResizable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);


        this.setLayout(new BorderLayout(20, 10));

        JPanel northPanel = new JPanel(new FlowLayout());

        JLabel labelNom = new JLabel("Nom");
        textFieldNom = new JTextField(10);

        JLabel labelPrenom = new JLabel("Prenom");
        textFieldPrenom = new JTextField(10);

        JLabel labelPseudo = new JLabel("Pseudo");
        textFieldPseudo = new JTextField(10);

        JButton validerButton = new JButton("Valider");
        //description de bouton
        validerButton.setToolTipText("Cliquer ici pour valider votre profil");

        // Add components to North panel
        northPanel.add(labelNom);
        northPanel.add(textFieldNom);
        northPanel.add(labelPrenom);
        northPanel.add(textFieldPrenom);
        northPanel.add(labelPseudo);
        northPanel.add(textFieldPseudo);
        northPanel.add(validerButton);



        this.add(northPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        this.add(splitPane, BorderLayout.CENTER);

        JLabel lb_help = new JLabel("Help");
        ImageIcon imageIcon = new ImageIcon("images/help.png");
        lb_help.setIcon(imageIcon);

        //add a margin to the help test :
        lb_help.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        this.add(lb_help,BorderLayout.SOUTH);




        //inisialiser le jlist et le modele:
        model = new DefaultListModel();
        jl = new JList();
        jl.setModel(model);
        //ajout des elements initiales:
//        model.addElement("ps1");

        jl.setPreferredSize(new Dimension(150,0));
        splitPane.setLeftComponent(jl);
//        model.addElement("ps2");
//        model.addElement("ps3");
        //initialiser jtabbed pane:
        jtp = new JTabbedPane();
//        jtp.addTab("titre1", new JPanel());

        //ajouter le jtabbedpane au centre de JFrame:
        splitPane.setRightComponent(jtp);



//        verifier la liste:
        if (!data.isEmpty()) {
            ajouterOnglet(data.get(0));
        }



        //si le nom existe le nom n'ajoute pas autre foit..
        validerButton.addActionListener(new ValiderButtonListener(this));



        //le curseur passe sur le label sont couleur change
        LabelMouseListener labelNomMouseListener = new LabelMouseListener(labelNom);
        labelNom.addMouseListener(labelNomMouseListener);

        LabelMouseListener labelPrenomMouseListener = new LabelMouseListener(labelPrenom);
        labelPrenom.addMouseListener(labelPrenomMouseListener);

        LabelMouseListener labelPseudoMouseListener = new LabelMouseListener(labelPseudo);
        labelPseudo.addMouseListener(labelPseudoMouseListener);




        //=>-la vous passer le curseur sur le zone de saisie le label help change de texte (exemple : saisir ici votre nom)) :
        // Création de l'instance de HelpMouseListener et ajout aux composants pertinents
        HelpMouseListener helpMouseListener = new HelpMouseListener(lb_help, textFieldNom, textFieldPrenom, textFieldPseudo);
        textFieldNom.addMouseListener(helpMouseListener);
        textFieldPrenom.addMouseListener(helpMouseListener);
        textFieldPseudo.addMouseListener(helpMouseListener);




//      travail maintenant "focus" :
        EcouteurFocus ecouteurFocus = new EcouteurFocus(lb_help, textFieldNom, textFieldPrenom, textFieldPseudo);
        textFieldNom.addFocusListener(ecouteurFocus);
        textFieldPrenom.addFocusListener(ecouteurFocus);
        textFieldPseudo.addFocusListener(ecouteurFocus);



        //cliquer le j liste est afficer les donners relier a l'utilisateur specifique
        jl.addMouseListener(new MouseAdapter() {


            @Override
            public void mouseClicked(MouseEvent e) {

                JList list = (JList) e.getSource();


                if (e.getClickCount()==2){

//                    String pseudoActuel = list.getSelectedValue().toString();
//
//                    for(Profil p : data){
//                        if(p.getPseudo().equals(pseudoActuel)){
//
//                            Formulaire f = new Formulaire(p);
//                            jtp.addTab(pseudoActuel,f);
//                            return;
//
//
//                        }
//
//                    }

                    String pseudoActuel = list.getSelectedValue().toString();
                    boolean ongletExiste = false;

                    for (TabForm onglet : onglets) {
                        if (onglet.getProfil().getPseudo().equals(pseudoActuel)) {
                            ongletExiste = true;
                            jtp.setSelectedComponent(onglet); // Activez l'onglet existant
                            break;
                        }
                    }

                    if (!ongletExiste) {
                        for (Profil p : data) {
                            if (p.getPseudo().equals(pseudoActuel)) {
                                ajouterOnglet(p); // Ajoutez l'onglet s'il n'existe pas
                                break;
                            }
                        }
                    }


                }

                //ajouter le lister si le droite de souris est cliquer:
                if(e.getButton()==MouseEvent.BUTTON3){  //bouton droit de souris


                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem  supprimerItem = new JMenuItem("Supprimer");
                    JMenuItem itemModifier = new JMenuItem("Modifier");
                    JMenuItem suppTous = new JMenuItem("Supprimer tous");
                    popupMenu.add(supprimerItem);
                    popupMenu.add(itemModifier);
                    popupMenu.add(suppTous);

                    popupMenu.show(jl,e.getX(),e.getY());

                    supprimerItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String pseudoSelectionne = (String) jl.getSelectedValue();

                            for (int i = 0; i < data.size(); i++) {
                                if (data.get(i).getPseudo().equals(pseudoSelectionne)) {

                                    data.remove(i);
                                    model.removeElement(pseudoSelectionne);
                                    supprimerOnglet(pseudoSelectionne);
                                    break;

                                }
                            }
                        }
                    });

                    itemModifier.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String pseudoSelectionne = (String) jl.getSelectedValue();

                            for (Profil profil : data) {
                                if (profil.getPseudo().equals(pseudoSelectionne)) {
                                    String nouveauNom = JOptionPane.showInputDialog(Gestion_Profil.this, "Nouveau nom :", profil.getNom());
                                    String nouveauPrenom = JOptionPane.showInputDialog(Gestion_Profil.this, "Nouveau prénom :", profil.getPrenom());

                                    profil.setNom(nouveauNom);
                                    profil.setPrenom(nouveauPrenom);

                                    // Mettre à jour le nouveau nom dans la liste jtp et les formulaires (onglets)
                                    for (int i = 0; i < jtp.getTabCount(); i++) {
                                        Component component = jtp.getComponentAt(i);
                                        if (component instanceof TabForm) {
                                            TabForm onglet = (TabForm) component;
                                            if (onglet.getProfil().equals(profil)) {
                                                onglet.updateProfil(profil); // Update the existing Formulaire instance
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });





                    //supprimer tous les donnes
                    suppTous.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            supprimerTousLesProfilsEtOnglets();

                        }
                    });

                }

            }
        });



        //ajouter la partie pour supprimer kes fenetres jtp






        this.setVisible(true);
    }


    //necessaires pour recuperer les donnes:
    public JTextField getTextFieldNom() {
        return textFieldNom;
    }

    public JTextField getTextFieldPrenom() {
        return textFieldPrenom;
    }

    public JTextField getTextFieldPseudo() {
        return textFieldPseudo;
    }

    public ArrayList<Profil> getData() {
        return data;
    }

    public DefaultListModel getModel() {
        return model;
    }





    //ajouter onglet
    private void ajouterOnglet(Profil profil) {
        TabForm onglet = new TabForm(profil);
        onglets.add(onglet);
        jtp.addTab(profil.getPseudo(), onglet);
    }

    //supprimer onglet
    private void supprimerOnglet(String pseudo) {
        for (TabForm onglet : onglets) {
            if (onglet.getProfil().getPseudo().equals(pseudo)) {
                jtp.remove(onglet);
                onglets.remove(onglet);
                return;
            }
        }
    }



    //supprimet profil et onglet associer
    private void supprimerProfilEtOnglet(String pseudoSelectionne) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getPseudo().equals(pseudoSelectionne)) {
                data.remove(i);
                model.removeElement(pseudoSelectionne);
                supprimerOnglet(pseudoSelectionne);
                break;
            }
        }
    }


    //supprimer tous
    private void supprimerTousLesProfilsEtOnglets() {
        data.clear();
        model.clear();
        jtp.removeAll(); // Supprimer tous les onglets
    }



}