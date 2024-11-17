package Formation.GestionFormation.Interfaces;


import Formation.GestionFormation.ButtonListenerAjoutETD;
import Formation.GestionFormation.Utils.EcouteurFocus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IHMAjoutETD extends JInternalFrame {

    private String placeholderNumCarte = "Entrez le numéro de carte";
    private String placeholderNom = "Entrez le nom";
    private String placeholderPrenom = "Entrez le prénom";

    public IHMAjoutETD() {
        setTitle("Ajout d'un étudiant");
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setSize(700, 400);

        // Centrage de la fenêtre
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        // Création des composants
        JLabel labelNumCarte = new JLabel("Numéro de carte ETD:");
        JTextField textFieldNumCarte = new JTextField(20);
        textFieldNumCarte.setText(placeholderNumCarte);

        JLabel labelNom = new JLabel("Nom:");
        JTextField textFieldNom = new JTextField(20);
        textFieldNom.setText(placeholderNom);

        JLabel labelPrenom = new JLabel("Prénom:");
        JTextField textFieldPrenom = new JTextField(20);
        textFieldPrenom.setText(placeholderPrenom);

        JLabel labelFiliere = new JLabel("Filière:");
        String[] filiereOptions = {"FIA", "ISI", "SI", "EAA", "GE", "GM", "MPI", "Autre"};
        JComboBox<String> comboBoxFiliere = new JComboBox<>(filiereOptions);

        JLabel labelNiveau = new JLabel("Niveau:");
        Integer[] niveauOptions = {1, 2, 3};
        JComboBox<Integer> comboBoxNiveau = new JComboBox<>(niveauOptions);

        JLabel labelGroupe = new JLabel("Groupe:");
        Integer[] groupeOptions = {1, 2, 3, 4, 5};
        JComboBox<Integer> comboBoxGroupe = new JComboBox<>(groupeOptions);

        JButton buttonAjouter = new JButton("Ajouter");
        buttonAjouter.setToolTipText("Cliquez ici pour ajouter l'étudiant");

        JButton buttonAnnuler = new JButton("Annuler");
        buttonAnnuler.setToolTipText("Cliquez ici pour annuler");

        // Ajout d'un écouteur de focus à chaque champ de texte
        textFieldNumCarte.addFocusListener(new EcouteurFocus(textFieldNumCarte, placeholderNumCarte));
        textFieldNom.addFocusListener(new EcouteurFocus(textFieldNom, placeholderNom));
        textFieldPrenom.addFocusListener(new EcouteurFocus(textFieldPrenom, placeholderPrenom));

        // Ajout d'un écouteur d'événements au bouton "Ajouter"
        buttonAjouter.addActionListener(new ButtonListenerAjoutETD(textFieldNumCarte, textFieldNom, textFieldPrenom,
                comboBoxFiliere, comboBoxNiveau, comboBoxGroupe));


        buttonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Création d'un panneau pour organiser les composants avec GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(labelNumCarte, gbc);
        gbc.gridx++;
        panel.add(textFieldNumCarte, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labelNom, gbc);
        gbc.gridx++;
        panel.add(textFieldNom, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labelPrenom, gbc);
        gbc.gridx++;
        panel.add(textFieldPrenom, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labelFiliere, gbc);
        gbc.gridx++;
        panel.add(comboBoxFiliere, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labelNiveau, gbc);
        gbc.gridx++;
        panel.add(comboBoxNiveau, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labelGroupe, gbc);
        gbc.gridx++;
        panel.add(comboBoxGroupe, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonAjouter, gbc);

        gbc.gridy++;
        panel.add(buttonAnnuler, gbc);

        // Ajout du panneau à la fenêtre interne
        getContentPane().add(panel);

        setVisible(true); // Afficher la fenêtre
    }
}
