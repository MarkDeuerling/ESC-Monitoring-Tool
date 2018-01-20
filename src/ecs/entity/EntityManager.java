package ecs.entity;

import ecs.component.IComponent;

import java.util.*;

/**
 * Created by deuer on 01.12.2016.
 */

/**
 * Represents the entity manager that provides methods to create, remove, get Entity's and subscribeSystem, get or remove IComponents.
 */
public final class EntityManager {

    private int nextIndex;
    private Entity current;
    private final Stack<Entity> pool;
    private final HashMap<Entity, HashMap<Class, IComponent>> componentData;

    /**
     * Initialize a new instance of EntityManager that has the default initial capacity.
     */
    public EntityManager() {
        this(7);
    }

    /**
     * Initialize a new instance of EntityManager that has the specified initial capacity.
     * @param capacity: The number of Entity's that the EventManager initially store.
     */
    public EntityManager(int capacity) {
        pool = new Stack<>();
        componentData = new HashMap<>(capacity);
    }

    /**
     * Sets an Entity to process subscribeSystem, get, remove and has Component methods.
     * @param entity: The entity to set.
     */
    public EntityManager setEntity(final Entity entity) {
        current = entity;
        return this;
    }

    /**
     * Gets the attached component set for each entity.
     * @return the componentData.
     */
    public HashMap<Entity, HashMap<Class, IComponent>> getComponentData() {
        return componentData;
    }

    /**
     * Gets the current entity.
     * @return the current set entity.
     */
    public Entity getEntity() {
        return current;
    }

    /**
     * Get all Entity's that has an IComponent.
     * @return an Set of Entity's.
     */
    public Set<Entity> getEntitySet() {
        return componentData.keySet();
    }

    /**
     * Creates an Entity and set it to the current entity.
     * @return this.
     */
    public EntityManager createsEntity() {
        current = createEntity();
        return this;
    }

    /**
     * Creates an Entity.
     * @return the created Entity.
     */
    public Entity createEntity() {
        if (nextIndex == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("GET FAIL: Too many Entities are present");
        }
        Entity e;
        if (pool.empty()) {
            e = new Entity();
            e.id = getID(nextIndex++);
        } else {
            e = pool.pop();
        }
        componentData.put(e, null);
        return e;
    }

    /**
     * Removes an Entity from the list. This method will not remove the components attached to the entity, use safeRemoveEntity(Entity) instead.
     * @param entity: The entity to remove. This parameter must not be null.
     * @return true if the entity was successfully removed.
     */
    public boolean removeEntity(final Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("GET FAIL: entity must not be null");
        }
        HashMap<Class, IComponent> datas = componentData.remove(entity);
        boolean hasEntity = datas != null;
        if (hasEntity) {
            pool.add(entity);
        }
        return hasEntity;
    }

    /**
     * Removes an Entity and attached components from the list and set.
     * @param entity: The entity to remove. This parameter must not be null.
     * @return true if the entity was successfully removed.
     */
    public boolean safeRemoveEntity(final Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("GET FAIL: entity must not be null");
        }
        componentData.remove(entity);
        return removeEntity(entity);
    }

    /**
     * Finds and returns the first founded entity with the given pattern or null.
     * @param args: The components that the entity should have attached (pattern).
     * @return The entity or null;
     */
    public Entity findEntity(Class<?> ... args) {
        ArrayList<Entity> entities = findEntities(args);
        return entities.isEmpty() ? null : entities.get(0);
    }

    /**
     * Finds all entities with the given components.
     * @param args: The components that the entity should have attached.
     * @return ArrayList<Entity>.
     */
    public ArrayList<Entity> findEntities(Class<?> ... args) {
        ArrayList<Entity> l = new ArrayList<>();
        boolean hasCachedComponent = false;
        for (Entity e : getEntitySet()) {
            for (Class<?> arg : args) {
                hasCachedComponent = hasComponent(e, arg);
                if (!hasCachedComponent) break;
            }
            if (hasCachedComponent) {
                l.add(e);
            }
        }
        return l;
    }

    /**
     * Adds an IComponent implemented class to the Entity.
     * @param entity: The entity to subscribeSystem the IComponent. This parameter must not be null.
     * @param componentClass: The component class. This parameter must not be null.
     * @param component: An instant of the component.
     * @return this.
     */
    public EntityManager addComponent(final Entity entity, final Class<?> componentClass, final IComponent component) {
        if (entity == null) {
            throw new IllegalArgumentException("GET FAIL: entity must not be null");
        }
        if (componentClass == null) {
            throw new IllegalArgumentException("GET FAIL: componentClass must not be null");
        }
        if (!componentClass.equals(component.getClass())) {
            throw new IllegalArgumentException("GET FAIL: component type not matching with the instance of component");
        }
        HashMap<Class, IComponent> components = componentData.get(entity);
        if (components == null) {
            components = new HashMap<>();
        } else {
            components = componentData.get(entity);
        }
        components.put(componentClass, component);
        componentData.put(entity, components);
        return this;
    }

    /**
     * Adds an IComponent implemented class to the Entity. Be sure to use setEntity(Entity) before using this method.
     * @param componentClass: The class of the component. This parameter must not be null.
     * @param component: An instant of the component.
     * @return this.
     */
    public EntityManager addComponent(final Class<?> componentClass, final IComponent component) {
        if (current == null) {
            throw new IllegalArgumentException("GET FAIL: use setEntity(Entity) before using this method");
        }
        return addComponent(current, componentClass, component);
    }

    /**
     * Gets an IComponent implemented class from an Entity.
     * @param entity: The entity that hold the component. This parameter must not be null.
     * @param componentClass: The class of the component. This parameter must not be null.
     * @param <T>: An IComponent implemented class.
     * @return the component or null if the component was not attached.
     */
    public <T extends IComponent> T getComponent(final Entity entity, final Class<T> componentClass) {
        if (entity == null) {
            throw new IllegalArgumentException("GET FAIL: entity must not be null");
        }
        if (componentClass == null) {
            throw new IllegalArgumentException("GET FAIL: componentClass must not be null");
        }
        HashMap<Class, IComponent> components = componentData.get(entity);
        return (T) components.get(componentClass);
    }

    /**
     * Gets an IComponent implemented class from the Entity. Be sure to use setEntity(Entity) before using this method.
     * @param componentClass: The class of the component: This parameter must not be null.
     * @param <T>: An IComponent implemented class.
     * @return the component or null if the component was nto attached.
     */
    public <T extends IComponent> T getComponent(final Class<T> componentClass) {
        if (current == null) {
            throw new IllegalArgumentException("GET FAIL: use setEntity(Entity) before using this method");
        }

        return getComponent(current, componentClass);
    }

    /**
     * Removes an IComponent implemented class from the Entity.
     * @param entity: The Entity to remove the component. This parameter must not be null.
     * @param componentClass: The class of the component. This parameter must not be null.
     * @return true if the component was successfully removed.
     */
    public boolean removeComponent(final Entity entity, final Class<?> componentClass) {
        if (entity == null) {
            throw new IllegalArgumentException("GET FAIL: entity must not be null");
        }
        if (componentClass == null) {
            throw new IllegalArgumentException("GET FAIL: componentClass must not be null");
        }
        HashMap<Class, IComponent> components = componentData.get(entity);
        return components.remove(componentClass) != null;
    }

    /**
     * Removes an IComponent implemented class from the Entity. Be sure to use setEntity(Entity) before using this method.
     * @param componentClass: The class of the component. This parameter must not be null.
     * @return true if the component was successfully removed.
     */
    public boolean removeComponent(final Class<?> componentClass) {
        if (componentClass == null) {
            throw new IllegalArgumentException("GET FAIL: componentClass must not be null");
        }
        return removeComponent(current, componentClass);
    }

    /**
     * Removes all components attached to an Entity. This parameter must not be null.
     * @param entity: The Entity to remove all components.
     */
    public void removeAllComponents(final Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("GET FAIL: entity must not be null");
        }
        componentData.put(entity, new HashMap<>());
    }

    /**
     * Checks if an Entity has an IComponent implemented class.
     * @param entity: The entity to check of the component. This parameter must not be null.
     * @param componentClass: The component to check. This parameter must not be null.
     * @return true if the component is attached.
     */
    public boolean hasComponent(final Entity entity, final Class<?> componentClass) {
        if (entity == null) {
            throw new IllegalArgumentException("GET FAIL: entity must not be null");
        }
        if (componentClass == null) {
            throw new IllegalArgumentException("GET FAIL: componentClass must not be null");
        }
        HashMap<Class, IComponent> components = componentData.get(entity);
        return components.get(componentClass) != null;
    }

    /**
     * Checks if an Entity has an IComponent implemented class. Be sure to use setEntity(Entity) before using this method.
     * @param componentClass: The class of the component. This parameter must not be null.
     * @return true if the component is attached.
     */
    public boolean hasComponent(final Class<?> componentClass) {
        if (current == null) {
            throw new IllegalArgumentException("GET FAIL: use setEntity(Entity) before using this method");
        }
        return hasComponent(current, componentClass);
    }

    private int getID(final int index) {
        int bitShift = 1 << 31;
        return index | bitShift;
    }

}