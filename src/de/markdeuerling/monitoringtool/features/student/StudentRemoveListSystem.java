package de.markdeuerling.monitoringtool.features.student;

import de.markdeuerling.monitoringtool.features.gui.View;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.system.IProcessSystem;

import javax.swing.*;

/**
 * Created by deuer on 23.12.2016.
 */
public class StudentRemoveListSystem implements IProcessSystem {

    private EntityManager entityManager;
    private View view;

    public  StudentRemoveListSystem(EntityManager entityManager, View view) {
        this.entityManager = entityManager;
        this.view = view;
    }

    @Override
    public void onProcess(Entity entity, Object data) {
        JList<String> list = view.getStudentListView();
        DefaultListModel<String> model = (DefaultListModel<String>)list.getModel();
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }
        String name = model.get(selectedIndex);
        model.remove(selectedIndex);
        entityManager
                .findEntities(Identity.class)
                .forEach(e -> {
                    if (entityManager.getComponent(e, Identity.class).name.equals(name)) {
                        entityManager.removeEntity(e);
                    }
                });
    }
}
