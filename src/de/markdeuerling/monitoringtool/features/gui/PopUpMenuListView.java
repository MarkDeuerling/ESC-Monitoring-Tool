package de.markdeuerling.monitoringtool.features.gui;

import javax.swing.*;

/**
 * Created by deuer on 07.01.2017.
 */
public class PopUpMenuListView {

    private JPopupMenu popupMenu;
    private JMenuItem removeItem;
    private JMenuItem showItem;
    private JMenuItem appointmentItem;
    private JMenuItem offTimeItem;

    public PopUpMenuListView() {
        buildMenu();
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public JMenuItem getRemoveItem() {
        return removeItem;
    }

    public JMenuItem getShowItem() {
        return showItem;
    }

    public JMenuItem getAppointmentItem() {
        return appointmentItem;
    }

    public JMenuItem getOffTimeItem() {
        return offTimeItem;
    }

    private void buildMenu() {
        popupMenu = new JPopupMenu();
        removeItem = new JMenuItem("Remove");
        showItem = new JMenuItem("Show");
        appointmentItem = new JMenuItem("Add Appointment");
        offTimeItem = new JMenuItem("Add Off Time");

        popupMenu.add(removeItem);
        popupMenu.add(showItem);
        popupMenu.add(appointmentItem);
        popupMenu.add(offTimeItem);
    }
}
