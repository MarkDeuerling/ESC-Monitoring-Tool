package de.markdeuerling.monitoringtool.features.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deuer on 22.12.2016.
 */
public class View {

    private JFrame frame;
    private JList<String> studentListView;
    private DefaultListModel<String> studentModel;
    private DefaultListModel<String> appointmentsModel;
    private JList<String> appointmentsListView;
    private JMenuBar menuBar;
    private JButton studentAddButton;
    private StudentAddWindow studentAddWindow;
    private PopUpMenuListView popUpMenuListView;
    private JTextArea textArea;
    private AppointmentWindow appointmentWindow;
    private OffTimeWindow offTimeWindow;
    private StudentShowWindow studentShowWindow;

    public View(String title, int width, int height) {
        buildWindow(title, width, height);
        addMenubar();
        addButton();
        addListView();
        showEventContent();
        addStudentEventListView();
        studentAddWindow = new StudentAddWindow();
        popUpMenuListView = new PopUpMenuListView();
        appointmentWindow = new AppointmentWindow();
        offTimeWindow = new OffTimeWindow();
        studentShowWindow = new StudentShowWindow();
    }

    public JFrame getFrame() {
        return frame;
    }

    public JList<String> getStudentListView() {
        return studentListView;
    }

    public DefaultListModel<String> getStudentModel() {
        return studentModel;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public JButton getStudentAddButton() {
        return studentAddButton;
    }

    public StudentAddWindow getStudentAddWindow() {
        return studentAddWindow;
    }

    public PopUpMenuListView getPopUpMenuListView() {
        return popUpMenuListView;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public DefaultListModel<String> getAppointmentsModel() {
        return appointmentsModel;
    }

    public JList<String> getAppointmentsListView() {
        return appointmentsListView;
    }

    public AppointmentWindow getAppointmentWindow() {
        return appointmentWindow;
    }

    public OffTimeWindow getOffTimeWindow() {
        return offTimeWindow;
    }

    public StudentShowWindow getStudentShowWindow() {
        return studentShowWindow;
    }

    private void addMenubar(){
        menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.add("Save");
        file.add("Clear Data");
        menuBar.add(file);
        frame.setJMenuBar(menuBar);
    }

    private void showEventContent() {
        textArea = new JTextArea("Select a student");
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 80));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.getContentPane().add(scrollPane, BorderLayout.EAST);
    }

    private void addStudentEventListView() {
        appointmentsModel = new DefaultListModel<>();

        appointmentsListView = new JList<>(appointmentsModel);
        appointmentsListView.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        appointmentsListView.setLayoutOrientation(JList.VERTICAL);
        appointmentsListView.setVisibleRowCount(-1);

        JScrollPane listScroller = new JScrollPane(appointmentsListView);
        listScroller.setPreferredSize(new Dimension(150, 80));

        frame.getContentPane().add(listScroller, BorderLayout.CENTER);
    }

    private void addListView() {
        studentModel = new DefaultListModel<>();

        studentListView = new JList<>(studentModel);
        studentListView.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        studentListView.setLayoutOrientation(JList.VERTICAL);
        studentListView.setVisibleRowCount(-1);

        JScrollPane listScroller = new JScrollPane(studentListView);
        listScroller.setPreferredSize(new Dimension(150, 80));

        frame.getContentPane().add(listScroller, BorderLayout.WEST);
    }

    private void addButton() {
        studentAddButton = new JButton("Add new Student");
        studentAddButton.setPreferredSize(new Dimension(150, 40));

        frame.getContentPane().add(studentAddButton, BorderLayout.SOUTH);
    }

    private JFrame buildWindow(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        return frame;
    }

}
