package ecs.event;

import ecs.entity.Entity;
import ecs.system.IProcessSystem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by deuer on 03.12.2016.
 */

/**
 * Represents an event manager to send messages between systems.
 * @param <T>: The enum where to find the different events.
 */
public final class EventManager<T extends Enum<T>> {

    private HashMap<T, ArrayList<IProcessSystem>> data;

    /**
     * Initializes a new instant of EventManager class that has the default initial capacity.
     */
    public EventManager() {
        this(7);
    }

    /**
     * Initialize a new instant of EventManager class that has the specified initial capacity.
     * @param capacity: The number of events that the EventManager can initially store.
     */
    public EventManager(final int capacity) {
        data = new HashMap<>(capacity);
    }

    /**
     * Registers a system to the EventManager.
     * @param system: The system to register. This parameter must not be null.
     * @param event: The event to bind the system. This parameter must not be null.
     */
    public void subscribeSystem(final IProcessSystem system, final T event) {
        if (system == null) {
            throw new IllegalArgumentException("GET FAIL: system must not be null");
        }
        if (event == null) {
            throw new IllegalArgumentException("GET FAIL: event must not be null");
        }
        ArrayList<IProcessSystem> systems = data.get(event);
        if (systems == null) {
            systems = new ArrayList<>();
        }
        systems.add(system);
        data.put(event, systems);
    }

    /**
     * Unregisters a system from the EventManager.
     * @param system: The system to unregister. This parameter must not be null.
     * @param event: The event to unbind the system. This parameter must not be null.
     */
    public void unsubscribeSystem(final IProcessSystem system, final T event) {
        if (system == null) {
            throw new IllegalArgumentException("GET FAIL: system must not be null");
        }
        if (event == null) {
            throw new IllegalArgumentException("GET FAIL: event must not be null");
        }
        ArrayList<IProcessSystem> systems = data.get(event);
        systems.remove(system);
    }

    /**
     * Sends a message to all register systems.
     * @param event: The event to fire. This parameter must not be null.
     * @param entity: The entity to process.
     * @param data: The data to process.
     */
    public void sendMessage(final Entity entity, final Object data, final T event) {
        if (event == null) {
            throw new IllegalArgumentException("GET FAIL: event must not be null");
        }
        ArrayList<IProcessSystem> systems = this.data.get(event);
        if (systems != null) {
            systems.forEach(d -> d.onProcess(entity, data));
        }
    }

}