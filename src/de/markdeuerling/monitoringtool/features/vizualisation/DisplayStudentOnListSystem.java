package de.markdeuerling.monitoringtool.features.vizualisation;

import de.markdeuerling.monitoringtool.features.gui.View;
import de.markdeuerling.monitoringtool.features.student.Address;
import de.markdeuerling.monitoringtool.features.student.Identity;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.system.IProcessSystem;


/**
 * Created by deuer on 23.12.2016.
 */
public class DisplayStudentOnListSystem implements IProcessSystem {

    private EntityManager entityManager;
    private View view;

    public DisplayStudentOnListSystem(EntityManager entityManager, View view) {
        this.entityManager = entityManager;
        this.view = view;
        //ON_ADD_STUDENT_TO_LIST
    }

    @Override
    public void onProcess(Entity entity, Object data) {
        view.getStudentModel().clear();
        entityManager
                .findEntities(Identity.class, Address.class)
                .forEach(e -> view.getStudentModel().addElement(entityManager.getComponent(e, Identity.class).name));
    }
}
