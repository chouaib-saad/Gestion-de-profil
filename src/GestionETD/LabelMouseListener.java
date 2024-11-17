package GestionETD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LabelMouseListener extends MouseAdapter {
    private JLabel label;

    public LabelMouseListener(JLabel label) {
        this.label = label;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        label.setForeground(Color.gray);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        label.setForeground(null);
    }
}
