package de.markdeuerling.monitoringtool.features.debug;

import ecs.component.IComponent;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.system.IUpdateSystem;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by deuer on 23.12.2016.
 */
public class DebugSystem implements IUpdateSystem {

    private EntityManager entityManager;

    public DebugSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void update() {
        int entitySizeWithComponent = entityManager.getEntitySet().size();
        System.out.println("----- Stored entity with component: " + entitySizeWithComponent);
        entityManager
                .getEntitySet()
                .forEach(entity -> {
                    System.out.println(String.format("Entity with id: %d has components of:", entity.id));
                    entityManager.getComponentData()
                            .get(entity)
                            .keySet()
                            .forEach(component -> System.out.println("-> " + component.getSimpleName()));
                });
    }
}
