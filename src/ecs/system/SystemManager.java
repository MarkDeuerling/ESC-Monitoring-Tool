package ecs.system;

import ecs.event.EventManager;

import java.util.ArrayList;

/**
 * Created by deuer on 01.12.2016.
 */

/**
 * Represents a system manager that provides methods to add and execute systems.
 */
public final class SystemManager {

    private ArrayList<IInitializeSystem> initSystems;
    private ArrayList<IUpdateSystem> updateSystems;
    private ArrayList<IProcessSystem> processSystems;

    /**
     * Adds an IInitializeSystem to the SystemManager.
     * @param system: The system to be added to the SystemManager. This parameter must not be null.
     * @return this.
     */
    public SystemManager add(final IInitializeSystem system) {
        if (system == null) {
            throw new IllegalArgumentException("GET FAIL: system must not be null");
        }
        if (initSystems == null) {
            initSystems = new ArrayList<>();
        }
        initSystems.add(system);
        return this;
    }

    /**
     * Removes an IInitializeSystem from the SystemManager.
     * @param system: The system to remove from the SystemManager. This parameter must not be null.
     * @return this.
     */
    public SystemManager remove(final IInitializeSystem system) {
        if (system == null) {
            throw new IllegalArgumentException("GET FAIL: system must not be null");
        }
        initSystems.remove(system);
        return this;
    }

    /**
     * Adds an IUpdateSystem to the SystemManager.
     * @param system: The system to be added to the SystemManager. This parameter must not be null.
     * @return this.
     */
    public SystemManager add(final IUpdateSystem system) {
        if (system == null) {
            throw new IllegalArgumentException("GET FAIL: system must not be null");
        }
        if (updateSystems == null) {
            updateSystems = new ArrayList<>();
        }
        updateSystems.add(system);
        return this;
    }

    /**
     * Removes an IUpdateSystem from the SystemManager.
     * @param system: The system to remove from teh SystemManager. This parameter must not be null.
     * @return
     */
    public SystemManager remove(final IUpdateSystem system) {
        if (system == null) {
            throw new IllegalArgumentException("GET FAIL: system must not be null");
        }
        updateSystems.remove(system);
        return this;
    }

    /**
     * Adds an IProcessSystem to the SystemManager.
     * @param system The system to be added to the SystemManager. This parameter must not be null.
     * @return this.
     */
    public SystemManager add(final IProcessSystem system) {
        if (system == null) {
            throw new IllegalArgumentException("GET FAIL: system must not be null");
        }
        if (processSystems == null) {
            processSystems = new ArrayList<>();
        }
        processSystems.add(system);
        return this;
    }

    /**
     * Removes an IProcessSystem from the SystemManager.
     * @param system The system to remove from the SystemManager. This parameter must not be null.
     * @return this.
     */
    public SystemManager remove(final IProcessSystem system) {
        if (system == null) {
            throw new IllegalArgumentException("GET FAIL: system must not be null");
        }
        processSystems.remove(system);
        return this;
    }

    /**
     * Adds an IProcessSystem to the SystemManager.
     * @param system: The system to be added to the SystemManager. This parameter must not be null.
     * @param eventManager: The reference to the EventManager. This parameter must not be null.
     * @param event: The event where to regist and fire the event.
     * @param <T>: The event enum.
     * @return this.
     */
    public <T extends Enum<T>> SystemManager add(final IProcessSystem system, EventManager<T> eventManager, T event) {
        if (system == null) {
            throw new IllegalArgumentException("GET FAIL: system must not be null");
        }
        if (eventManager == null) {
            throw new IllegalArgumentException("GET FAIL: eventManager must not be null");
        }
        if (event == null) {
            throw new IllegalArgumentException("GET FAIL: event must not be null");
        }
        eventManager.subscribeSystem(system, event);
        return this;
    }

    /**
     * Removes an IProcessSystem to the SystemManager.
     * @param system: The system to remove from the SystemManager. This parameter must not be null.
     * @param eventManager: The reference to the EventManager. This parameter must not be null.
     * @param event: The event where to unregist and fire the event.
     * @param <T>: The event enum.
     * @return this.
     */
    public <T extends Enum<T>> SystemManager remove(final IProcessSystem system, EventManager<T> eventManager, T event) {
        if (system == null) {
            throw new IllegalArgumentException("GET FAIL: system must not be null");
        }
        if (eventManager == null) {
            throw new IllegalArgumentException("GET FAIL: eventManager must not be null");
        }
        if (event == null) {
            throw new IllegalArgumentException("GET FAIL: event must not be null");
        }
        eventManager.unsubscribeSystem(system, event);
        return this;
    }

    /**
     * Calls all IInitializeSystem.onStartup() in the added order.
     */
    public void startup() {
        if (initSystems == null) {
            return;
        }
        initSystems.forEach(IInitializeSystem::onStartup);
    }

    /**
     * Calls all IInitializeSystem.onShutdown() in a reverse added order.
     */
    public void shutdown() {
        if (initSystems == null) {
            return;
        }
        int size = initSystems.size();
        for (int i = 0; i < size; ++i) {
            IInitializeSystem system = initSystems.get(size - 1 - i);
            system.onShutdown();
        }
    }

    /**
     * Calls all IUpdateSystem.update() in the added order.
     */
    public void update() {
        if (updateSystems == null) {
            return;
        }
        updateSystems.forEach(IUpdateSystem::update);
    }

}

