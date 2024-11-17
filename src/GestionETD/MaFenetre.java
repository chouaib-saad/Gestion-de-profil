package GestionETD;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MaFenetre extends JInternalFrame implements ActionListener {
    private JTextField tfNomPrenom, tfEmail, tfImageUpload;
    private JComboBox<Integer> cbJour, cbMois, cbAnnee;
    private JRadioButton rbFemme, rbHomme;
    private JComboBox<String> cbFormation, cbJava, cbPython, cbML;
    private JTextArea taCommentaires;
    private JCheckBox cbEnregistrerPDF;
    private JButton btnEnvoyer, btnAnnuler, btnImageUpload;

    MaFenetre() {
        this.setTitle("Curriculum Vitae");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setClosable(true);
        this.setResizable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 0, 0));
        JLabel titleLabel = new JLabel("Les informations personnelles");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
        headerPanel.add(titleLabel);





        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel personalInfoPanel = new JPanel(new BorderLayout(10, 10));
        personalInfoPanel.setBackground(new Color(240, 240, 240));

        // Create a compound border with a line border and an empty border for margin
        Border compoundBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), // Black line border
                BorderFactory.createEmptyBorder(8, 8, 8, 8) // Empty border for margin
        );

        JLabel lib_titre = new JLabel("Informations personnelles");
        lib_titre.setForeground(Color.BLACK);
        lib_titre.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
        lib_titre.setBackground(new Color(255, 255, 255));
        lib_titre.setOpaque(true);
        lib_titre.setHorizontalAlignment(JLabel.CENTER);

        // Apply the compound border to the panel
        personalInfoPanel.setBorder(compoundBorder);
        personalInfoPanel.add(lib_titre, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        leftPanel.setBackground(new Color(240, 240, 240));
        leftPanel.add(new JLabel("Nom et prénom:"));
        leftPanel.add(new JLabel("Email:"));
        leftPanel.add(new JLabel("Date de naissance:"));
        leftPanel.add(new JLabel("Sexe:"));
        leftPanel.add(new JLabel("Formation:"));
        leftPanel.add(new JLabel("Image:"));

        JPanel rightPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        rightPanel.setBackground(new Color(240, 240, 240));
        tfNomPrenom = new JTextField();
        rightPanel.add(tfNomPrenom);
        tfEmail = new JTextField();
        rightPanel.add(tfEmail);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBackground(new Color(240, 240, 240));

        cbJour = new JComboBox<>(generateNumbers(1, 31));
        cbMois = new JComboBox<>(generateNumbers(1, 12));
        cbAnnee = new JComboBox<>(generateNumbers(1960, 2024));

        cbAnnee.setSelectedItem(2024);

        datePanel.add(cbJour);
        datePanel.add(cbMois);
        datePanel.add(cbAnnee);
        rightPanel.add(datePanel);


        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(new Color(240, 240, 240));
        rbFemme = new JRadioButton("Femme");
        rbHomme = new JRadioButton("Homme");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbFemme);
        genderGroup.add(rbHomme);
        genderPanel.add(rbFemme);
        genderPanel.add(rbHomme);
        rightPanel.add(genderPanel);

        String[] formationOptions = {"Ingénieur", "Étudiant en info", "Étudiant en TIC"};
        cbFormation = new JComboBox<>(formationOptions);
        rightPanel.add(cbFormation);

        JPanel imageUploadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imageUploadPanel.setBackground(new Color(240, 240, 240));

        btnImageUpload = new JButton("Importer votre photo");
        btnImageUpload.addActionListener(this);

        imageUploadPanel.add(btnImageUpload);
        rightPanel.add(imageUploadPanel);

        personalInfoPanel.add(leftPanel, BorderLayout.WEST);
        personalInfoPanel.add(rightPanel, BorderLayout.CENTER);

        mainPanel.add(personalInfoPanel, BorderLayout.NORTH);

        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

        JPanel skillsPanel = new JPanel(new BorderLayout(10, 10));
        skillsPanel.setBackground(new Color(240, 240, 240));

        JLabel skillsTitle = new JLabel("Compétences techniques");
        skillsTitle.setForeground(Color.BLACK);
        skillsTitle.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
        skillsTitle.setBackground(new Color(255, 255, 255));
        skillsTitle.setOpaque(true);
        skillsTitle.setHorizontalAlignment(JLabel.CENTER);

        // Apply the compound border to the panel
        skillsPanel.setBorder(compoundBorder);
        skillsPanel.add(skillsTitle, BorderLayout.NORTH);

        JPanel skillsLeftPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        skillsLeftPanel.setBackground(new Color(240, 240, 240));
        skillsLeftPanel.add(new JLabel("Java:"));
        skillsLeftPanel.add(new JLabel("Python:"));
        skillsLeftPanel.add(new JLabel("Machine Learning:"));
        skillsLeftPanel.add(new JLabel("Commentaires:"));


        JPanel skillsRightPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        skillsRightPanel.setBackground(new Color(240, 240, 240));

        String[] javaOptions = {"Expert","Débutant", "Intermédiaire"};
        cbJava = new JComboBox<>(javaOptions);
        skillsRightPanel.add(cbJava);

        String[] pythonOptions = {"Débutant", "Intermédiaire", "Expert"};
        cbPython = new JComboBox<>(pythonOptions);
        skillsRightPanel.add(cbPython);

        String[] mlOptions = {"Débutant", "Intermédiaire", "Expert"};
        cbML = new JComboBox<>(mlOptions);
        skillsRightPanel.add(cbML);

        taCommentaires = new JTextArea();
        JScrollPane commentScrollPane = new JScrollPane(taCommentaires);
        skillsRightPanel.add(commentScrollPane);

        skillsPanel.add(skillsLeftPanel, BorderLayout.WEST);
        skillsPanel.add(skillsRightPanel, BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        optionsPanel.setBackground(new Color(240, 240, 240));

        cbEnregistrerPDF = new JCheckBox("Enregistrer une version PDF de la candidature avant d'envoyer");
        optionsPanel.add(cbEnregistrerPDF);

        mainPanel.add(optionsPanel, BorderLayout.SOUTH);

        JPanel buttonsPanel = new JPanel(new BorderLayout(10, 10));
        buttonsPanel.setBackground(new Color(240, 240, 240));

        JCheckBox cbVisible = new JCheckBox("Enregistrer une version PDF de la candidature avant d'envoyer");
        buttonsPanel.add(cbVisible, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnEnvoyer = new JButton("Envoyer");
        btnAnnuler = new JButton("Annuler");
        btnPanel.add(btnEnvoyer);
        btnPanel.add(btnAnnuler);
        buttonsPanel.add(btnPanel, BorderLayout.CENTER);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        mainPanel.add(skillsPanel, BorderLayout.CENTER);

        this.add(mainPanel);

        btnEnvoyer.addActionListener(this);
        btnAnnuler.addActionListener(this);
        btnImageUpload.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAnnuler) {
            System.exit(0);
        } else if (e.getSource() == btnEnvoyer) {
            // Save to HTML file
            File f = new File("cv.html");
            try {
                FileWriter fw = new FileWriter(f);
                fw.write("<html>");
                fw.write("<head><title>Curriculum Vitae</title></head>");
                fw.write("<body>");

                fw.write("<h1>Les informations personnelles</h1>");
                fw.write("<p><strong>Nom et prénom :</strong> " + tfNomPrenom.getText() + "</p>");
                fw.write("<p><strong>Email :</strong> " + tfEmail.getText() + "</p>");
                fw.write("<p><strong>Date de naissance :</strong> " + cbJour.getSelectedItem() + "/"
                        + cbMois.getSelectedItem() + "/" + cbAnnee.getSelectedItem() + "</p>");
                fw.write("<p><strong>Genre :</strong> " + (rbFemme.isSelected() ? "Femme" : "Homme") + "</p>");
                fw.write("<p><strong>Formation :</strong> " + cbFormation.getSelectedItem() + "</p>");
                fw.write("<p><strong>Image :</strong> <img src='" + "' width='200' height='200'></p>");
                fw.write("</div>");

                fw.write("<hr>");

                fw.write("<div>");
                fw.write("<h1>Compétences techniques</h1>");
                fw.write("<p><strong>Java :</strong> " + cbJava.getSelectedItem() + "</p>");
                fw.write("<p><strong>Python :</strong> " + cbPython.getSelectedItem() + "</p>");
                fw.write("<p><strong>Machine Learning :</strong> " + cbML.getSelectedItem() + "</p>");
                fw.write("<p><strong>Commentaires :</strong> " + taCommentaires.getText() + "</p>");
                fw.write("</div>");

                fw.write("</body>");
                fw.write("</html>");

                fw.close();
                Desktop.getDesktop().open(f);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == btnImageUpload) {
            // Add functionality for image upload button here
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                tfImageUpload.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    private Integer[] generateNumbers(int start, int end) {
        Integer[] numbers = new Integer[end - start + 1];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = start + i;
        }
        return numbers;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MaFenetre());
    }
}
