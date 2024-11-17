package Formation.Exercice1;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class EcouteurFocusF extends FocusAdapter {
    private JTextField textField;
    private String placeholder;

    public EcouteurFocusF (JTextField textField, String placeholder) {
        this.textField = textField;
        this.placeholder = placeholder;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (textField.getText().equals(placeholder)) {
            textField.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (textField.getText().isEmpty()) {
            textField.setText(placeholder);
        }
    }
}
