package de.markdeuerling.monitoringtool.features.vizualisation;

import de.markdeuerling.monitoringtool.features.appointments.NextAppointmentIn24Hours;
import de.markdeuerling.monitoringtool.features.gui.View;
import de.markdeuerling.monitoringtool.features.mentoring.Mentoring;
import de.markdeuerling.monitoringtool.features.student.Identity;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.system.IProcessSystem;

import java.util.ArrayList;

/**
 * Created by deuer on 02.03.2017.
 */
public class DisplayNextAppointmentSystem implements IProcessSystem {

    private EntityManager entityManager;
    private View view;

    public DisplayNextAppointmentSystem(EntityManager entityManager, View view) {
        this.entityManager = entityManager;
        this.view = view;
        // GRAPHIC_UPDATE
    }

    @Override
    public void onProcess(Entity entity, Object data) {
        String name = view.getStudentListView().getSelectedValue();
        if (name == null) {
            return;
        }
        String message = "No Meetings in the next 24 hours";
        view.getTextArea().setText(message);
        StringBuilder stringBuilder = new StringBuilder(20);
        ArrayList<Entity> appointments = entityManager.findEntities(Mentoring.class, NextAppointmentIn24Hours.class);
        if (appointments.isEmpty()) {
            return;
        }
        ArrayList<Entity> students = new ArrayList<>(appointments.size());
        appointments.forEach(e -> {
            if (entityManager.getComponent(e, Identity.class).name.equals(name)) {
                students.add(e);
            }
        });
        if (students.isEmpty()) {
            return;
        }
        students.forEach(e -> {
            Mentoring mentoring = entityManager.getComponent(e, Mentoring.class);
            stringBuilder
                    .append(mentoring.description)
                    .append(" ")
                    .append(mentoring.nextTalk)
                    .append("\n");
        });
        if (stringBuilder.length() == 0) {
            view.getTextArea().setText(message);
        } else {
            view.getTextArea().setText(stringBuilder.toString());
        }
    }
}
