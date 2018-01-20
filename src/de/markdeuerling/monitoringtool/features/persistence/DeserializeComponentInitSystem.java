package de.markdeuerling.monitoringtool.features.persistence;

import de.markdeuerling.monitoringtool.blueprint.Blueprints;
import de.markdeuerling.monitoringtool.events.SystemEvent;
import ecs.component.IComponent;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.event.EventManager;
import ecs.system.IInitializeSystem;
import flexjson.JSONDeserializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by deuer on 03.02.2017.
 */
public class DeserializeComponentInitSystem implements IInitializeSystem {

    private EntityManager entityManager;
    private EventManager<SystemEvent> eventManager;

    public DeserializeComponentInitSystem(EntityManager entityManager, EventManager<SystemEvent> eventManager) {
        this.entityManager = entityManager;
        this.eventManager = eventManager;
    }

    @Override
    public void onStartup() {
        if (!new File(Blueprints.JSON_FILE).exists()) {
            return;
        }
        try {
            HashMap<Entity, HashMap<Class, IComponent>> datas =
                    new JSONDeserializer<HashMap<Entity, HashMap<Class, IComponent>>>()
                            .deserialize(new FileReader(Blueprints.JSON_FILE));

            for (Map.Entry<Entity, HashMap<Class, IComponent>> entry : datas.entrySet()) {
                HashMap<Class, IComponent> map = entry.getValue();
                Entity entity = entityManager.createEntity();
                for (Map.Entry<Class, IComponent> components : map.entrySet()) {
                    Class componentClass = components.getValue().getClass();
                    IComponent component = components.getValue();
                    entityManager.addComponent(entity, componentClass, component);
                }
            }
            eventManager.sendMessage(null, null, SystemEvent.ON_ADD_STUDENT_TO_LIST);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onShutdown() {
        // serialize all entities
        eventManager.sendMessage(null, null, SystemEvent.ON_SERIALIZE);
    }
}
