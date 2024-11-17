package GestionETD;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HelpMouseListener extends MouseAdapter {
    private JLabel lb_help;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldPseudo;

    public HelpMouseListener(JLabel lb_help, JTextField textFieldNom, JTextField textFieldPrenom, JTextField textFieldPseudo) {
        this.lb_help = lb_help;
        this.textFieldNom = textFieldNom;
        this.textFieldPrenom = textFieldPrenom;
        this.textFieldPseudo = textFieldPseudo;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getComponent() instanceof JTextField) {
            JTextField textField = (JTextField) e.getComponent();
            if (textField == textFieldNom) {
                lb_help.setText("Saisir votre nom..");
            } else if (textField == textFieldPrenom) {
                lb_help.setText("Saisir votre prénom..");
            } else if (textField == textFieldPseudo) {
                lb_help.setText("Saisir votre pseudo..");
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        lb_help.setText("Help");
    }
}
