package de.markdeuerling.monitoringtool.features.offTime;

import de.markdeuerling.monitoringtool.events.SystemEvent;
import ecs.event.EventManager;
import ecs.system.IInitializeSystem;

/**
 * Created by deuer on 03.03.2017.
 */
public class OffTimeInitSystem implements IInitializeSystem {

    private EventManager<SystemEvent> eventManager;

    public OffTimeInitSystem(EventManager<SystemEvent> eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void onStartup() {
        eventManager.sendMessage(null, null, SystemEvent.ON_OFF_TIME);
    }

    @Override
    public void onShutdown() {

    }
}
