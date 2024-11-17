package Formation.GestionFormation.Interfaces;


import Formation.GestionFormation.ButtonListenerAjoutF;
import Formation.GestionFormation.DAO.FormationDAO;
import Formation.GestionFormation.Utils.EcouteurFocus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IHMAjoutF extends JInternalFrame {

    private String placeholderRef = "Entrez la Reference";
    private String placeholderTitre = "Entrez le Titre";
    private String placeholderDate = "AAAA/MM/JJ";
    private String placeholderLieu = "Entrez le lieu";

    private FormationDAO formationDAO;

    public IHMAjoutF() {
        setTitle("Ajout d'une formation");
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconifiable(true);
        setSize(700, 400);

        // Centrage de la fenetre
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        formationDAO = new FormationDAO(); // Assuming FormationDAO handles its own connection internally

        // Création des composants
        JLabel labelRef = new JLabel("Référence:");
        JTextField textFieldRef = new JTextField(20);
        textFieldRef.setText(placeholderRef);

        JLabel labelTitre = new JLabel("Titre:");
        JTextField textFieldTitre = new JTextField(20);
        textFieldTitre.setText(placeholderTitre);


        JLabel labeldate = new JLabel("Date:");
        JTextField textFielddate = new JTextField(20);
        textFielddate.setText(placeholderDate);


        JLabel labellieu = new JLabel("Lieu:");
        JTextField textFieldlieu = new JTextField(20);
        textFieldlieu.setText(placeholderLieu);


        JLabel labelcertif = new JLabel("Certification :");
        JCheckBox checkBoxCertification = new JCheckBox("Certification");

        JButton buttonValider = new JButton("Ajouter");
        buttonValider.setToolTipText("Cliquer ici pour ajouter la formation");
        JButton buttonAnnuler = new JButton("Annuler");
        buttonAnnuler.setToolTipText("Cliquer ici pour annuler");


        // Ajout de l'écouteur de focus à chaque champ de texte
        textFieldRef.addFocusListener(new EcouteurFocus(textFieldRef, placeholderRef));
        textFieldTitre.addFocusListener(new EcouteurFocus(textFieldTitre, placeholderTitre));
        textFielddate.addFocusListener(new EcouteurFocus(textFielddate, placeholderDate));
        textFieldlieu.addFocusListener(new EcouteurFocus(textFieldlieu, placeholderLieu));


        // Ajout d'un écouteur d'événements au bouton "Ajouter"
        buttonValider.addActionListener(new ButtonListenerAjoutF(textFieldRef, textFieldTitre, textFielddate, textFieldlieu, checkBoxCertification));

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

        panel.add(labelRef, gbc);

        gbc.gridx = 1;
        panel.add(textFieldRef, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labelTitre, gbc);

        gbc.gridx = 1;
        panel.add(textFieldTitre, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labeldate, gbc);

        gbc.gridx = 1;
        panel.add(textFielddate, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labellieu, gbc);

        gbc.gridx = 1;
        panel.add(textFieldlieu, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labelcertif, gbc);

        gbc.gridx = 1;
        panel.add(checkBoxCertification, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonValider, gbc);

        gbc.gridy++;
        panel.add(buttonAnnuler, gbc);

        // Ajout du panneau à la fenêtre interne
        getContentPane().add(panel);

        setVisible(true); // Afficher la fenêtre
    }
}
