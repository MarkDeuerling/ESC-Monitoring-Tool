package de.markdeuerling.monitoringtool.features.initialization;

import de.markdeuerling.monitoringtool.events.SystemEvent;
import de.markdeuerling.monitoringtool.features.gui.PopUpMenuListView;
import de.markdeuerling.monitoringtool.features.gui.StudentShowWindow;
import de.markdeuerling.monitoringtool.features.gui.View;
import de.markdeuerling.monitoringtool.features.student.Address;
import de.markdeuerling.monitoringtool.features.student.Identity;
import ecs.entity.EntityManager;
import ecs.event.EventManager;
import ecs.system.IInitializeSystem;

import java.awt.event.ActionEvent;

/**
 * Created by deuer on 26.02.2017.
 */
public class ListViewPopupInitSystem implements IInitializeSystem {

    private View view;
    private EntityManager entityManager;
    private EventManager<SystemEvent> eventManager;

    public ListViewPopupInitSystem(View view, EventManager<SystemEvent> eventManager, EntityManager entityManager) {
        this.view = view;
        this.eventManager = eventManager;
        this.entityManager = entityManager;
    }

    @Override
    public void onStartup() {
        PopUpMenuListView menu = view.getPopUpMenuListView();
        menu.getRemoveItem().addActionListener(this::onRemove);
        menu.getShowItem().addActionListener(this::onShowWindow);
        menu.getAppointmentItem().addActionListener(this::onAppointment);
        menu.getOffTimeItem().addActionListener(this::onOffTime);
    }

    @Override
    public void onShutdown() {
        // not in use
    }

    private void onRemove(ActionEvent e) {
        eventManager.sendMessage(null, null, SystemEvent.ON_REMOVE_STUDENT);
    }

    private void onShowWindow(ActionEvent e) {
        String name = view.getStudentListView().getSelectedValue();
        entityManager
                .findEntities(Identity.class, Address.class)
                .forEach(entity -> {
                    Identity id = entityManager.getComponent(entity, Identity.class);
                    Address address = entityManager.getComponent(entity, Address.class);
                    if (id.name.equals(name)) {
                        String email = address.email;
                        String matnr = address.matNr;
                        StudentShowWindow studentShowWindow = view.getStudentShowWindow();
                        studentShowWindow.getName().setText(name);
                        studentShowWindow.getEmail().setText(email);
                        studentShowWindow.getMatNr().setText(matnr);
                    }
                });
        view.getStudentShowWindow().show();
    }

    private void onAppointment(ActionEvent e) {
        view.getAppointmentWindow().show();
    }

    private void onOffTime(ActionEvent e) {
        view.getOffTimeWindow().show();
    }
}
