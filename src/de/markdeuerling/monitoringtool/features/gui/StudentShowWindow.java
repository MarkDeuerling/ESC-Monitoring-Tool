package de.markdeuerling.monitoringtool.features.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deuer on 26.02.2017.
 */
public class StudentShowWindow {

    private JFrame frame;
    private JPanel panel;
    private JTextField name;
    private JTextField matNr;
    private JTextField email;

    public StudentShowWindow() {
        panel = new JPanel();
        name = new JTextField("Name");
        matNr = new JTextField("MatNr");
        email = new JTextField("Email");

        panel.setLayout(new GridLayout(3, 0));

        panel.add(name);
        panel.add(matNr);
        panel.add(email);

        frame = new JFrame("Show");
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(300, 180));
        frame.getContentPane().add(panel);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getName() {
        return name;
    }

    public JTextField getMatNr() {
        return matNr;
    }

    public JTextField getEmail() {
        return email;
    }
}
