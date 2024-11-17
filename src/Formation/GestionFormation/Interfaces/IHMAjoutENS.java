package Formation.GestionFormation.Interfaces;

import Formation.GestionFormation.ButtonListenerAjoutENS;
import Formation.GestionFormation.Utils.EcouteurFocus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IHMAjoutENS extends JInternalFrame {

    private String placeholderNumCarte = "Entrez le numéro de carte";
    private String placeholderNom = "Entrez le nom";
    private String placeholderPrenom = "Entrez le prénom";
    private String placeholderSpecialite = "Entrez la spécialité";

    public IHMAjoutENS() {
        setTitle("Ajout d'un enseignant");
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
        JLabel labelNumCarte = new JLabel("Numero carte cin:");
        JTextField textFieldNumCarte = new JTextField(20);
        textFieldNumCarte.setText(placeholderNumCarte);

        JLabel labelNom = new JLabel("Nom:");
        JTextField textFieldNom = new JTextField(20);
        textFieldNom.setText(placeholderNom);

        JLabel labelPrenom = new JLabel("Prenom:");
        JTextField textFieldPrenom = new JTextField(20);
        textFieldPrenom.setText(placeholderPrenom);

        JLabel labelSpecialite = new JLabel("Specialite:");
        JTextField textFieldSpecialite = new JTextField(20);
        textFieldSpecialite.setText(placeholderSpecialite);

        JLabel labelGrade = new JLabel("Grade:");
        String[] grades = {"Assistant", "Maitre assistant", "Maitre de conferences", "Professeur de l'enseignement superieur", "Docteur"};
        JComboBox<String> comboBoxGrade = new JComboBox<>(grades);

        JButton buttonValider = new JButton("Valider");
        buttonValider.setToolTipText("Cliquez ici pour valider");

        JButton buttonAnnuler = new JButton("Annuler");
        buttonAnnuler.setToolTipText("Cliquez ici pour annuler");

        // Ajout d'un écouteur de focus à chaque champ de texte
        textFieldNumCarte.addFocusListener(new EcouteurFocus(textFieldNumCarte, placeholderNumCarte));
        textFieldNom.addFocusListener(new EcouteurFocus(textFieldNom, placeholderNom));
        textFieldPrenom.addFocusListener(new EcouteurFocus(textFieldPrenom, placeholderPrenom));
        textFieldSpecialite.addFocusListener(new EcouteurFocus(textFieldSpecialite, placeholderSpecialite));

        // Ajout d'un écouteur d'événements au bouton "Ajouter"
        buttonValider.addActionListener(new ButtonListenerAjoutENS(textFieldNumCarte, textFieldNom, textFieldPrenom,
                textFieldSpecialite,comboBoxGrade));


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
        panel.add(labelSpecialite, gbc);
        gbc.gridx++;
        panel.add(textFieldSpecialite, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(labelGrade, gbc);
        gbc.gridx++;
        panel.add(comboBoxGrade, gbc);

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
