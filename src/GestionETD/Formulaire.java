package GestionETD;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Formulaire extends JPanel {
    private JLabel label;
    private JLabel avatarLabel;
    private Profil profil;

    Formulaire(Profil profil) {
        this.profil = profil;

        // Set background color to white
        setBackground(Color.WHITE);

        // Create a GridBagLayout to center all content
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Avatar label
        avatarLabel = new JLabel();
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the avatar horizontally
        avatarLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin
        ImageIcon avatarIcon = new ImageIcon("images\\user.png"); // Create the avatar icon
        avatarLabel.setIcon(avatarIcon);
        add(avatarLabel, gbc);

        // User data label
        label = new JLabel(getProfileHTMLText());
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the text horizontally
        add(label, gbc);
    }

    // Create an avatar icon (placeholder for illustration)
    private ImageIcon createAvatarIcon() {
        // Placeholder implementation, you should replace this with actual avatar image loading logic
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        // Draw a circle with a gradient fill to represent the avatar
        GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE, 100, 100, Color.CYAN);
        g2d.setPaint(gradient);
        g2d.fillOval(10, 10, 80, 80); // Adjusted the size of the oval
        g2d.dispose();
        return new ImageIcon(image);
    }

    // Generate HTML text for displaying profile information
    private String getProfileHTMLText() {
        String htmlText = "<html>";
        htmlText += "<b>Nom:</b> " + profil.getNom() + "<br>";
        htmlText += "<b>Prénom:</b> " + profil.getPrenom() + "<br>";
        // Add more profile data as needed
        htmlText += "</html>";
        return htmlText;
    }

    // Method to update the profile information displayed
    public void updateProfil(Profil newProfil) {
        this.profil = newProfil;
        label.setText(getProfileHTMLText()); // Update the user data label
//        avatarLabel.setIcon(createAvatarIcon()); // Update the avatar icon
    }
}
