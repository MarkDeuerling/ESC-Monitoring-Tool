package ecs.entity;

/**
 * Created by deuer on 01.12.2016.
 */

/**
 * Represents an Entity. Do never instantiate this class, instead use EntityManager.createEntity().
 */
public final class Entity {
    public int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
