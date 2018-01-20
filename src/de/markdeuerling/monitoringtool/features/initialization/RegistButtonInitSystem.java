package de.markdeuerling.monitoringtool.features.initialization;

import de.markdeuerling.monitoringtool.events.SystemEvent;
import de.markdeuerling.monitoringtool.features.gui.AppointmentWindow;
import de.markdeuerling.monitoringtool.features.gui.OffTimeWindow;
import de.markdeuerling.monitoringtool.features.gui.View;
import de.markdeuerling.monitoringtool.features.mentoring.Mentoring;
import de.markdeuerling.monitoringtool.features.offTime.OffTime;
import de.markdeuerling.monitoringtool.features.student.Identity;
import ecs.entity.EntityManager;
import ecs.event.EventManager;
import ecs.system.IInitializeSystem;

import java.awt.event.ActionEvent;

/**
 * Created by deuer on 23.12.2016.
 */
public class RegistButtonInitSystem implements IInitializeSystem {

    private EntityManager entityManager;
    private EventManager<SystemEvent> eventManager;
    private View view;

    public RegistButtonInitSystem(EntityManager entityManager, EventManager<SystemEvent> eventEventManager, View view) {
        this.entityManager = entityManager;
        this.eventManager = eventEventManager;
        this.view = view;
    }

    @Override
    public void onStartup() {
        view.getStudentAddButton().addActionListener(this::openStudentAddWindow);
        view.getStudentAddWindow().getApplyButton().addActionListener(this::addStudentToList);
        view.getAppointmentWindow().getApplyButton().addActionListener(this::addAppointment);
        view.getOffTimeWindow().getApplyButton().addActionListener(this::addOffTime);
    }

    @Override
    public void onShutdown() {
        // not in use
    }

    private void addOffTime(ActionEvent e) {
        String name = view.getStudentListView().getSelectedValue();
        OffTimeWindow offTimeWindow = view.getOffTimeWindow();
        Identity id = new Identity();
        id.name = name;
        OffTime offTime = new OffTime();
        offTime.count = Integer.parseInt(offTimeWindow.getCount().getText());
        entityManager
                .createsEntity()
                .addComponent(Identity.class, id)
                .addComponent(OffTime.class, offTime);
        offTimeWindow.hide();
        eventManager.sendMessage(null, null, SystemEvent.ON_OFF_TIME);
        eventManager.sendMessage(null, null, SystemEvent.GRAPHIC_UPDATE);
    }

    private void addAppointment(ActionEvent e) {
        String name = view.getStudentListView().getSelectedValue();
        AppointmentWindow appointmentWindow = view.getAppointmentWindow();
        Identity id = new Identity();
        id.name = name;
        Mentoring mentoring = new Mentoring();
        String day = appointmentWindow.getDay().getText();
        String month = appointmentWindow.getMonth().getText();
        String year = appointmentWindow.getYear().getText();
        String hour = appointmentWindow.getHour().getText();
        String minute = appointmentWindow.getMinute().getText();
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        mentoring.nextTalk = day + "." + month + "." + year + " " + hour + ":" + minute;
        mentoring.description = appointmentWindow.getDescription().getText();
        entityManager
                .createsEntity()
                .addComponent(Identity.class, id)
                .addComponent(Mentoring.class, mentoring);
        appointmentWindow.hide();
        eventManager.sendMessage(null, null, SystemEvent.TIME_UPDATE);
        eventManager.sendMessage(null, null, SystemEvent.GRAPHIC_UPDATE);
    }

    private void openStudentAddWindow(ActionEvent e) {
        view.getStudentAddWindow().getFrame().setVisible(true);
    }

    private void addStudentToList(ActionEvent e) {
        eventManager.sendMessage(null, null, SystemEvent.CONTROL_STUDENT);
    }

}
