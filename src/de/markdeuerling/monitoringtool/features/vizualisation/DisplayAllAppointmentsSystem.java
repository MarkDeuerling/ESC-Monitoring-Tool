package de.markdeuerling.monitoringtool.features.vizualisation;

import de.markdeuerling.monitoringtool.features.gui.View;
import de.markdeuerling.monitoringtool.features.mentoring.Mentoring;
import de.markdeuerling.monitoringtool.features.offTime.Expire;
import de.markdeuerling.monitoringtool.features.offTime.OffTime;
import de.markdeuerling.monitoringtool.features.student.Address;
import de.markdeuerling.monitoringtool.features.student.Identity;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.system.IProcessSystem;

import java.util.ArrayList;

/**
 * Created by deuer on 02.03.2017.
 */
public class DisplayAllAppointmentsSystem implements IProcessSystem {

    private EntityManager entityManager;
    private View view;

    public DisplayAllAppointmentsSystem(EntityManager entityManager, View view) {
        this.entityManager = entityManager;
        this.view = view;
    }

    @Override
    public void onProcess(Entity entity, Object data) {
        String name = view.getStudentListView().getSelectedValue();
        if (name == null) {
            return;
        }
        ArrayList<Entity> students = entityManager.findEntities(Identity.class, Address.class);
        for (Entity student : students) {
            Identity studentID = entityManager.getComponent(student, Identity.class);
            if (studentID.name.equals(name)) {
                view.getAppointmentsModel().clear();
                entityManager
                        .findEntities(Identity.class)
                        .forEach(e -> {
                            Mentoring mentoring = entityManager.getComponent(e, Mentoring.class);
                            Identity id = entityManager.getComponent(e, Identity.class);
                            OffTime offTime = entityManager.getComponent(e, OffTime.class);
                            Expire expire = entityManager.getComponent(e, Expire.class);
                            if (id.name.equals(name)) {
                                if (mentoring != null) {
                                    view.getAppointmentsModel().addElement(mentoring.description +
                                            " " + mentoring.nextTalk);
                                }
                                if (offTime != null) {
                                    view.getAppointmentsModel().addElement("Off Time: " + offTime.count);
                                }
                                if (expire != null) {
                                    view.getAppointmentsModel().addElement("Due to an off-time over 3 month, " +
                                            "the student could not pass the exam");
                                }
                            }
                        });
                break;
            }
        }
    }
}
