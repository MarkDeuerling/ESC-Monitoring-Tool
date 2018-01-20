package de.markdeuerling.monitoringtool.features.offTime;

import de.markdeuerling.monitoringtool.features.student.Address;
import de.markdeuerling.monitoringtool.features.student.Identity;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.system.IProcessSystem;

import java.util.ArrayList;

/**
 * Created by deuer on 26.02.2017.
 */
public class OffTimeSystem implements IProcessSystem {

    private EntityManager entityManager;

    public OffTimeSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
        // ON_OFF_TIME
    }

    @Override
    public void onProcess(Entity entity, Object data) {
        int maxOffTime = 84;
        ArrayList<Entity> students = entityManager.findEntities(Identity.class, Address.class);
        ArrayList<Entity> offTimes = entityManager.findEntities(OffTime.class);
        entityManager
                .findEntities(Expire.class)
                .forEach(e -> entityManager.removeEntity(e));
        for (Entity student : students) {
            Identity studentID = entityManager.getComponent(student, Identity.class);
            int count = 0;
            for (Entity offTime : offTimes) {
                if (studentID.name.equals(entityManager.getComponent(offTime, Identity.class).name)) {
                    count += entityManager.getComponent(offTime, OffTime.class).count;
                }
            }
            if (count > maxOffTime) {
                Identity id = new Identity();
                id.name = studentID.name;
                entityManager
                        .createsEntity()
                        .addComponent(Expire.class, new Expire())
                        .addComponent(Identity.class, id);
            }
        }
    }
}
