package de.markdeuerling.monitoringtool.features.vizualisation;

import de.markdeuerling.monitoringtool.features.appointments.NextAppointmentIn24Hours;
import de.markdeuerling.monitoringtool.features.gui.View;
import de.markdeuerling.monitoringtool.features.offTime.Expire;
import de.markdeuerling.monitoringtool.features.student.Identity;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.system.IProcessSystem;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by deuer on 02.03.2017.
 */
public class DisplayExpireSystem implements IProcessSystem {

    private EntityManager entityManager;
    private View view;

    public DisplayExpireSystem(EntityManager entityManager, View view) {
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
        JTextArea textArea = view.getTextArea();
        String description = "Due to an off-time over 3 month, the student could not pass the exam";
        boolean hasNextAppointment = false;
        ArrayList<Entity> nextAppointments = entityManager.findEntities(NextAppointmentIn24Hours.class);
        for (Entity e : nextAppointments) {
            if (name.equals(entityManager.getComponent(e, Identity.class).name)) {
                hasNextAppointment = true;
                break;
            }
        }
        ArrayList<Entity> expires = entityManager.findEntities(Expire.class);
        for (Entity e : expires) {
            if (name.equals(entityManager.getComponent(e, Identity.class).name)) {
                if (hasNextAppointment) {
                    textArea.append(description);
                } else {
                    textArea.setText(description);
                }
                break;
            }
        }

    }
}
