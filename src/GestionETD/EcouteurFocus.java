package GestionETD;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class EcouteurFocus implements FocusListener {

    private JLabel lb_help;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldPseudo;

    EcouteurFocus(JLabel lb_help, JTextField textFieldNom, JTextField textFieldPrenom, JTextField textFieldPseudo) {
        this.lb_help = lb_help;
        this.textFieldNom = textFieldNom;
        this.textFieldPrenom = textFieldPrenom;
        this.textFieldPseudo = textFieldPseudo;
        initialise();

    }

    @Override
    public void focusGained(FocusEvent e) {

        if (e.getComponent() instanceof JTextField) {
            JTextField textField = (JTextField) e.getComponent();
            if (textField == textFieldNom) {

                if(textFieldNom.getText().equals("Saisir le nom")) {
                    textFieldNom.setText("");
                }

            } else if (textField == textFieldPrenom) {

                if(textFieldPrenom.getText().equals("Saisir le prenom")) {
                    textFieldPrenom.setText("");
                }

            } else if (textField == textFieldPseudo) {


                if(textFieldPseudo.getText().equals("Saisir le pseudo")) {
                    textFieldPseudo.setText("");
                }

            }
        }


    }

    @Override
    public void focusLost(FocusEvent e) {


        JTextField textField = (JTextField) e.getComponent();
        if (textField == textFieldNom) {

            if(textFieldNom.getText().isEmpty()) {


                textFieldNom.setText("Saisir le nom");

            }

        } else if (textField == textFieldPrenom) {

            if(textFieldPrenom.getText().isEmpty()) {

                textFieldPrenom.setText("Saisir le prenom");

            }

        } else if (textField == textFieldPseudo) {


            if(textFieldPseudo.getText().isEmpty()) {

                textFieldPseudo.setText("Saisir le pseudo");

            }

        }
    }



    public void initialise(){

        textFieldNom.setText("Saisir le nom");
        textFieldPrenom.setText("Saisir le prenom");
        textFieldPseudo.setText("Saisir le pseudo");

    }


}
