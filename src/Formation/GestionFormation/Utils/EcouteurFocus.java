package Formation.GestionFormation.Utils;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class EcouteurFocus extends FocusAdapter {
    private JTextField textField;
    private String placeholder;

    public EcouteurFocus(JTextField textField, String placeholder) {
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
