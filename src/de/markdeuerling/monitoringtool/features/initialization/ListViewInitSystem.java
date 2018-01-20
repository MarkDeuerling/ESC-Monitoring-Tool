package de.markdeuerling.monitoringtool.features.initialization;

import de.markdeuerling.monitoringtool.events.SystemEvent;
import de.markdeuerling.monitoringtool.features.gui.PopUpMenuListView;
import de.markdeuerling.monitoringtool.features.gui.View;
import ecs.entity.EntityManager;
import ecs.event.EventManager;
import ecs.system.IInitializeSystem;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by deuer on 23.12.2016.
 */
public class ListViewInitSystem implements IInitializeSystem {

    private EventManager<SystemEvent> eventManager;
    private EntityManager entityManager;
    private View view;

    public  ListViewInitSystem(View view, EventManager<SystemEvent> eventManager, EntityManager entityManager) {
        this.eventManager = eventManager;
        this.entityManager = entityManager;
        this.view = view;
    }

    @Override
    public void onStartup() {
        // init entity with selection component
        JList list = view.getStudentListView();
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (list.getSelectedIndex() >= 0) {
                    eventManager.sendMessage(null, null, SystemEvent.GRAPHIC_UPDATE);
                }
            }
        });
        // display context menu on selected student name
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) &&
                        !list.isSelectionEmpty() &&
                        list.locationToIndex(e.getPoint()) == list.getSelectedIndex()) {

                    PopUpMenuListView menu = view.getPopUpMenuListView();
                    menu.getPopupMenu().show(list, e.getX(), e.getY());
                }
            }
        });
    }

    @Override
    public void onShutdown() {
        // not in use
    }
}
