package Formation.FormationV2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

public class GestionEtudiant extends JInternalFrame {

    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldCIN;
    private JTextField textFieldMoyenne;
    private JLabel lb_help;
    private JTable jt_etud;
//    private MyTableModel model;
    private String placeholderNom = "Entrez le Nom";
    private String placeholderPrenom = "Entrez le Prénom";
    private String placeholderCIN = "Entrez le CIN";
    private String placeholderMoyenne = "Entrez la Moyenne";

    private Formation.FormationV2.EtdDAO dao;


    private String nom;
    private String prenom;
    private double moyenne;
    private int cin;

    public GestionEtudiant()  {
        this.setTitle("Gestion Etudiant");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setClosable(true);
        this.setResizable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);

        this.setLayout(new BorderLayout(20, 10));

        lb_help = new JLabel("Aide");
        lb_help.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));

        JPanel northPanel = new JPanel(new FlowLayout());

        JLabel labelNom = new JLabel("Nom");
        labelNom.setForeground(Color.BLACK);
        labelNom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                labelNom.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelNom.setForeground(Color.BLACK);
            }
        });
        textFieldNom = new JTextField(10);
        textFieldNom.setText(placeholderNom);
        textFieldNom.addFocusListener(new Formation.FormationV2.EcouteurFocus(lb_help, textFieldNom, placeholderNom));

        JLabel labelPrenom = new JLabel("Prenom");
        labelPrenom.setForeground(Color.BLACK);
        labelPrenom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                labelPrenom.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelPrenom.setForeground(Color.BLACK);
            }
        });
        textFieldPrenom = new JTextField(10);
        textFieldPrenom.setText(placeholderPrenom);
        textFieldPrenom.addFocusListener(new Formation.FormationV2.EcouteurFocus(lb_help, textFieldPrenom, placeholderPrenom));

        JLabel labelCIN = new JLabel("CIN");
        labelCIN.setForeground(Color.BLACK);
        labelCIN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                labelCIN.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelCIN.setForeground(Color.BLACK);
            }
        });
        textFieldCIN = new JTextField(10);
        textFieldCIN.setText(placeholderCIN);
        textFieldCIN.addFocusListener(new Formation.FormationV2.EcouteurFocus(lb_help, textFieldCIN, placeholderCIN));

        JLabel labelMoyenne = new JLabel("Moyenne");
        labelMoyenne.setForeground(Color.BLACK);
        labelMoyenne.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                labelMoyenne.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelMoyenne.setForeground(Color.BLACK);
            }
        });
        textFieldMoyenne = new JTextField(10);
        textFieldMoyenne.setText(placeholderMoyenne);
        textFieldMoyenne.addFocusListener(new Formation.FormationV2.EcouteurFocus(lb_help, textFieldMoyenne, placeholderMoyenne));

        JButton validerButton = new JButton("Valider");
        validerButton.setToolTipText("Cliquer ici pour valider l'étudiant");
        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                if (textFieldNom.getText().equals("Entrez le Nom") || textFieldPrenom.getText().equals("Entrez le Prénom") || textFieldCIN.getText().equals("Entrez le CIN") ||   textFieldMoyenne.getText().equals("Entrez la Moyenne")) {
                    JOptionPane.showMessageDialog(GestionEtudiant.this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                //verifier les types des donnes :
                try {
                    cin = Integer.parseInt(textFieldCIN.getText());
                    moyenne = Double.parseDouble(textFieldMoyenne.getText());
                    nom = textFieldNom.getText();
                    prenom = textFieldPrenom.getText();
                } catch (NumberFormatException error) {

                    JOptionPane.showMessageDialog(GestionEtudiant.this, "Erreur : "+error.getMessage(), "Type de donner ne pas accepté", JOptionPane.ERROR_MESSAGE);
                    return;
                }


//                JOptionPane.showMessageDialog(GestionEtudiant.this, "Etudiant ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);


                    // Clear fields after adding
                textFieldNom.setText("Entrez le Nom");
                textFieldPrenom.setText("Entrez le Prénom");
                textFieldCIN.setText("Entrez le CIN");
                textFieldMoyenne.setText("Entrez la Moyenne");


//                model.insertEtudiant(nom,prenom,cin,moyenne);

            }
        });

        northPanel.add(labelNom);
        northPanel.add(textFieldNom);
        northPanel.add(labelPrenom);
        northPanel.add(textFieldPrenom);
        northPanel.add(labelCIN);
        northPanel.add(textFieldCIN);
        northPanel.add(labelMoyenne);
        northPanel.add(textFieldMoyenne);
        northPanel.add(validerButton);

        this.add(northPanel, BorderLayout.NORTH);
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        this.add(lb_help, BorderLayout.SOUTH);

        // Initialize JTable and Model
//        jt_etud = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(jt_etud);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setVisible(true);



//        jt_etud = new JTable(t1,t2);
        String req = "select* From Etudiant";
        dao = new EtdDAO(Config.URL, Config.USERNAME, Config.PASSWORD);
        ResultSet rs = dao.selection(req);
//        model = new MyTableModel(rs,dao);
//        jt_etud.setModel(model);

        jt_etud.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JTable table = (JTable) e.getSource();





                //ajouter le lister si le droite de souris est cliquer:
                if(e.getButton()==MouseEvent.BUTTON3){  //bouton droit de souris


                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem  supprimerEtudiant = new JMenuItem("Supprimer");
                    JMenuItem suppTous = new JMenuItem("Supprimer tous");
                    popupMenu.add(supprimerEtudiant);
                    popupMenu.add(suppTous);

                    popupMenu.show(table,e.getX(),e.getY());

                    supprimerEtudiant.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {


                            int choix = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet étudiant ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

                            if (choix == JOptionPane.YES_OPTION) {

                                //supression:


                                int row = jt_etud.getSelectedRow();
                                if (row == -1) {
                                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un étudiant à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
//                                int cin = (int) model.getValueAt(row, 2); // Supposons que le CIN se trouve à l'indice 2 dans le modèle de tableau
                                // Supprimer l'étudiant de la base de données en utilisant la méthode supprimerEtudiant de la classe EtudiantDAO
                                int rowsAffected = dao.supprimerEtudiant(cin);
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(null, "Étudiant supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                                    // Mise à jour de la table
//                                    model.supprimerEtudiant(row);
                                }



                            } else {
                                //rien ne faire..


                            }


                        }
                    });







                    //supprimer tous les donnes
                    suppTous.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {


                            int choix = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer tous les étudiants ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);


                            //supression:

                            if (choix == JOptionPane.YES_OPTION) {
                                // Supprimer tous les étudiants de la base de données en utilisant la méthode supprimerTous de la classe EtudiantDAO
                                int rowsAffected = dao.supprimerTous();
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(null, "Tous les étudiants ont été supprimés avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                                    // Mise à jour de la table
//                                    model.viderTable();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression des étudiants.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                // Ne rien faire si l'utilisateur a choisi "Non"
                            }


                        }


                    });

                }



            }
        });

    }


}