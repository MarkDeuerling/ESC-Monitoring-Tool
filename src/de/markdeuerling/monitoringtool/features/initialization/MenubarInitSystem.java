package de.markdeuerling.monitoringtool.features.initialization;

import de.markdeuerling.monitoringtool.events.SystemEvent;
import de.markdeuerling.monitoringtool.features.gui.View;
import ecs.entity.EntityManager;
import ecs.event.EventManager;
import ecs.system.IInitializeSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by deuer on 01.02.2017.
 */
public class MenubarInitSystem implements IInitializeSystem {

    private View view;
    private EventManager<SystemEvent> eventManager;
    private EntityManager entityManager;

    public MenubarInitSystem(View view, EventManager<SystemEvent> eventManager, EntityManager entityManager) {
        this.view = view;
        this.eventManager = eventManager;
        this.entityManager = entityManager;
    }

    @Override
    public void onStartup() {
        // todo: more save
        JMenuItem save = view.getMenuBar().getMenu(0).getItem(0);
        JMenuItem clear = view.getMenuBar().getMenu(0).getItem(1);
        save.addActionListener(this::addSave);
        clear.addActionListener(this::addClear);
    }

    @Override
    public void onShutdown() {
        // not in use
    }

    private void addClear(ActionEvent e) {
        entityManager.getComponentData().clear();
        view.getStudentModel().clear();
        view.getTextArea().setText("");
        view.getAppointmentsModel().clear();
        eventManager.sendMessage(null, null, SystemEvent.GRAPHIC_UPDATE);
        eventManager.sendMessage(null, null, SystemEvent.TIME_UPDATE);
    }

    private void addSave(ActionEvent e) {
        eventManager.sendMessage(null, null, SystemEvent.ON_SERIALIZE);
    }
}
