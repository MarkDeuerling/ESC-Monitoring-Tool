package de.markdeuerling.monitoringtool.features.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deuer on 25.02.2017.
 */
public class OffTimeWindow {

    private JTextField count;
    private JButton button;
    private JFrame frame;
    private JPanel panel;

    public OffTimeWindow() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0));

        count = new JTextField("Count in days");
        button = new JButton("Add");

        panel.add(count);
        panel.add(button);

        frame = new JFrame("Add Off Time");
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(300, 130));
        frame.getContentPane().add(panel);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

    public JTextField getCount() {
        return count;
    }

    public JButton getApplyButton() {
        return button;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }
}
