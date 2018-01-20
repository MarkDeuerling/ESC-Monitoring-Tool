package de.markdeuerling.monitoringtool.features.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deuer on 25.02.2017.
 */
public class AppointmentWindow {
    private JFrame frame;
    private JPanel panel;
    private JTextField day;
    private JTextField month;
    private JTextField year;
    private JTextField hour;
    private JTextField minute;
    private JTextField description;
    private JButton button;

    public AppointmentWindow() {
        content();
        frame = new JFrame("Add Appointment");
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

    public JTextField getDay() {
        return day;
    }

    public JTextField getMonth() {
        return month;
    }

    public JTextField getYear() {
        return year;
    }

    public JTextField getHour() {
        return hour;
    }

    public JTextField getMinute() {
        return minute;
    }

    public JTextField getDescription() {
        return description;
    }

    public JButton getApplyButton() {
        return button;
    }

    private void content() {
        panel = new JPanel();
        day = new JTextField("Day");
        month = new JTextField("Month");
        year = new JTextField("Year");
        hour = new JTextField("hour");
        minute = new JTextField("minute");
        description = new JTextField("Description");
        button = new JButton("Add");

        panel.setLayout(new GridLayout(7, 0));

        panel.add(day);
        panel.add(month);
        panel.add(year);
        panel.add(hour);
        panel.add(minute);
        panel.add(description);
        panel.add(button);
    }

}
