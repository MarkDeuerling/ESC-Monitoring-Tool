package de.markdeuerling.monitoringtool.features.vizualisation;

import de.markdeuerling.monitoringtool.features.appointments.NextAppointmentIn24Hours;
import de.markdeuerling.monitoringtool.features.gui.View;
import de.markdeuerling.monitoringtool.features.offTime.Expire;
import de.markdeuerling.monitoringtool.features.student.Address;
import de.markdeuerling.monitoringtool.features.student.Identity;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.system.IProcessSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by deuer on 02.03.2017.
 */
public class DisplayAsteriskSystem implements IProcessSystem {

    private EntityManager entityManager;
    private View view;

    public DisplayAsteriskSystem(EntityManager entityManager, View view) {
        this.entityManager = entityManager;
        this.view = view;
        // GRAPHIC_UPDATE
    }

    @Override
    public void onProcess(Entity entity, Object data) {
        ArrayList<Entity> students = entityManager.findEntities(Identity.class, Address.class);
        ArrayList<Entity> expireEntities = entityManager.findEntities(Expire.class);
        ArrayList<Entity> nextAppointmentEntities = entityManager.findEntities(NextAppointmentIn24Hours.class);
        DefaultListModel<String> model = view.getStudentModel();
        HashSet<String> needAsteriskNames = new HashSet<>(expireEntities.size() + nextAppointmentEntities.size());
        // remove all asterisk on ids
        entityManager
                .findEntities(Identity.class)
                .forEach(e -> {
                    Identity id = entityManager.getComponent(e, Identity.class);
                    int idx = id.name.indexOf('*');
                    if (idx > -1) {
                        id.name = id.name.substring(0, idx);
                    }
                });
        // remove all asterisk on model name
        for (int i = 0; i < model.size(); ++i) {
            String name = model.getElementAt(i);
            int idx = name.indexOf('*');
            if (idx > -1) {
                name = name.substring(0, idx);
                model.setElementAt(name, i);
            }
        }
        // add all name to the list that need asterisk from expire and nextAppointment
        for (Entity student : students) {
            Identity studentID = entityManager.getComponent(student, Identity.class);
            for (Entity expire : expireEntities) {
                Identity expireID = entityManager.getComponent(expire, Identity.class);
                if (studentID.name.equals(expireID.name)) {
                    needAsteriskNames.add(studentID.name);
                }
            }
            for (Entity nextAppointment : nextAppointmentEntities) {
                Identity nextAppointmentID = entityManager.getComponent(nextAppointment, Identity.class);
                if (studentID.name.equals(nextAppointmentID.name)) {
                    needAsteriskNames.add(studentID.name);
                }
            }
        }
        // add asterisk to all names if needed
        needAsteriskNames.forEach(s -> entityManager
                .findEntities(Identity.class)
                .forEach(e -> {
                    Identity id = entityManager.getComponent(e, Identity.class);
                    if (id.name.equals(s)) {
                        id.name += '*';

                    }
                })
        );
        // add asterisk to student model
        needAsteriskNames.forEach(s -> {
            for (int i = 0; i < model.size(); ++i) {
                String name = model.getElementAt(i);
                if (name.equals(s)) {
                    model.setElementAt(name + "*", i);
                }
            }
        });
    }
}