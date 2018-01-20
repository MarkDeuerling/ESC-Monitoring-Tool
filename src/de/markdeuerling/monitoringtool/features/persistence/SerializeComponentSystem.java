package de.markdeuerling.monitoringtool.features.persistence;

import de.markdeuerling.monitoringtool.blueprint.Blueprints;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.system.IProcessSystem;
import flexjson.JSONSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by deuer on 03.02.2017.
 */
public class SerializeComponentSystem implements IProcessSystem {

    private EntityManager entityManager;

    public SerializeComponentSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void onProcess(Entity entity, Object data) {
        JSONSerializer serializer = new JSONSerializer();
        serializer.prettyPrint(true);
        String json = serializer.serialize(entityManager.getComponentData());
        try {
            FileWriter fw = new FileWriter(new File(Blueprints.JSON_FILE));
            fw.write(json);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
