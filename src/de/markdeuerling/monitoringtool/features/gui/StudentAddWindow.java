package de.markdeuerling.monitoringtool.features.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deuer on 06.01.2017.
 */
public class StudentAddWindow {

    private JFrame frame;
    private JTextField name;
    private JTextField matNr;
    private JTextField email;
    private JButton applyButton;
    private JPanel panel;

    public JFrame getFrame() {
        return frame;
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

    public JButton getApplyButton() {
        return applyButton;
    }

    public JPanel getPanel() {
        return panel;
    }

    public StudentAddWindow() {
        panel = new JPanel();
        addItems("Apply", "Name", "MatNr.", "Email");
        buildWindow("Create student", 300, 180);
    }

    private void addItems(String buttonName, String textName, String textMatNr, String textEmail) {
        applyButton = new JButton(buttonName);
        name = new JTextField(textName);
        matNr = new JTextField(textMatNr);
        email = new JTextField(textEmail);

        panel.setLayout(new GridLayout(4, 0));

        panel.add(name);
        panel.add(matNr);
        panel.add(email);
        panel.add(applyButton);
    }

    private void buildWindow(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(width, height));
        frame.getContentPane().add(panel);
    }

}
