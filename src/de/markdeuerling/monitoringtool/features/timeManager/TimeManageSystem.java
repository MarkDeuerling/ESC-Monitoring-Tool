package de.markdeuerling.monitoringtool.features.timeManager;

import de.markdeuerling.monitoringtool.events.SystemEvent;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.event.EventManager;
import ecs.system.IInitializeSystem;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by deuer on 26.02.2017.
 */
public class TimeManageSystem implements IInitializeSystem {

    private EventManager<SystemEvent> eventManager;
    private EntityManager entityManager;

    public TimeManageSystem(EventManager<SystemEvent> eventManager, EntityManager entityManager) {
        this.eventManager = eventManager;
        this.entityManager = entityManager;
    }

    @Override
    public void onStartup() {
        TimeFormat timeFormat = new TimeFormat();
        timeFormat.format = "dd.MM.yyyy HH:mm";
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Entity e = entityManager.findEntity(TimeFormat.class);
                if (e == null) {
                    entityManager
                            .createsEntity()
                            .addComponent(TimeFormat.class, timeFormat);
                }
                eventManager.sendMessage(null, null, SystemEvent.TIME_UPDATE);
                eventManager.sendMessage(null, null, SystemEvent.GRAPHIC_UPDATE);
            }
        }, 0,1000 * 60); // 1 min
    }

    @Override
    public void onShutdown() {
        // nothing todo
    }
}
