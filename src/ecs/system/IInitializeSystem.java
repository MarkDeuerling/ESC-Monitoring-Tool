package ecs.system;

/**
 * Created by deuer on 01.12.2016.
 */
public interface IInitializeSystem extends ISystem {
    void onStartup();
    void onShutdown();
}
