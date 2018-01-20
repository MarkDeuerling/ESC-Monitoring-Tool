package ecs.system;

import ecs.entity.Entity;

/**
 * Created by deuer on 01.12.2016.
 */
public interface IProcessSystem extends ISystem {
    void onProcess(Entity entity, Object data);
}
