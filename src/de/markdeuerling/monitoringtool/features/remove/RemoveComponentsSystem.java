package de.markdeuerling.monitoringtool.features.remove;

import de.markdeuerling.monitoringtool.features.appointments.NextAppointmentIn24Hours;
import de.markdeuerling.monitoringtool.features.offTime.Expire;
import de.markdeuerling.monitoringtool.features.timeManager.TimeFormat;
import ecs.entity.EntityManager;
import ecs.system.IInitializeSystem;

/**
 * Created by deuer on 02.03.2017.
 */
public class RemoveComponentsSystem implements IInitializeSystem {

    private EntityManager entityManager;

    public RemoveComponentsSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void onStartup() {
        entityManager
                .findEntities(TimeFormat.class)
                .forEach(e -> entityManager.removeEntity(e));
        entityManager
                .findEntities(Expire.class)
                .forEach(e -> entityManager.removeEntity(e));
        entityManager
                .findEntities(NextAppointmentIn24Hours.class)
                .forEach(e -> entityManager.removeEntity(e));
    }

    @Override
    public void onShutdown() {
        // nothing todo
    }
}
