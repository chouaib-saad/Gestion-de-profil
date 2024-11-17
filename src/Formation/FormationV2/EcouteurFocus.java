package Formation.FormationV2;
import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class EcouteurFocus extends FocusAdapter {
    private JLabel helpLabel;
    private JTextField textField;
    private String placeholder;

    public EcouteurFocus(JLabel helpLabel, JTextField textField, String placeholder) {
        this.helpLabel = helpLabel;
        this.textField = textField;
        this.placeholder = placeholder;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (textField.getText().equals(placeholder)) {
            textField.setText("");
        }
        if (helpLabel != null) {
            helpLabel.setText(placeholder);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (textField.getText().isEmpty()) {
            textField.setText(placeholder);
        }
        if (helpLabel != null) {
            helpLabel.setText("Help");
        }
    }
}