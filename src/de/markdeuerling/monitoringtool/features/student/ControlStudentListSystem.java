package de.markdeuerling.monitoringtool.features.student;

import de.markdeuerling.monitoringtool.events.SystemEvent;
import de.markdeuerling.monitoringtool.features.gui.StudentAddWindow;
import de.markdeuerling.monitoringtool.features.gui.View;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.event.EventManager;
import ecs.system.IProcessSystem;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deuer on 03.03.2017.
 */
public class ControlStudentListSystem implements IProcessSystem {

    private EventManager<SystemEvent> eventManager;
    private EntityManager entityManager;
    private View view;

    public ControlStudentListSystem(EventManager<SystemEvent> eventManager, EntityManager entityManager, View view) {
        this.eventManager = eventManager;
        this.entityManager = entityManager;
        this.view = view;
        // CONTROL_STUDENT
    }

    @Override
    public void onProcess(Entity entity, Object data) {
        DefaultListModel<String> model = view.getStudentModel();
        StudentAddWindow window = view.getStudentAddWindow();
        boolean hasFoundDouble = false;
        for (int i = 0; i < model.size(); ++i) {
            if (window.getName().getText().equals(model.getElementAt(i))) {
                hasFoundDouble = true;
                break;
            }
        }
        if (hasFoundDouble) {
            System.out.println("Name is already present");
            Toolkit.getDefaultToolkit().beep();
        } else {
            Entity student = entityManager.createEntity();
            Identity id = new Identity();
            Address address = new Address();
            id.name = window.getName().getText();
            address.matNr = window.getMatNr().getText();
            address.email = window.getEmail().getText();
            window.getFrame().setVisible(false);
            entityManager
                    .addComponent(student, Identity.class, id)
                    .addComponent(student, Address.class, address);
            eventManager.sendMessage(null, null, SystemEvent.ON_ADD_STUDENT_TO_LIST);
        }
    }
}
