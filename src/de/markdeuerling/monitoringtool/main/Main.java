package de.markdeuerling.monitoringtool.main;

import de.markdeuerling.monitoringtool.events.SystemEvent;
import de.markdeuerling.monitoringtool.features.initialization.ListViewInitSystem;
import de.markdeuerling.monitoringtool.features.initialization.ListViewPopupInitSystem;
import de.markdeuerling.monitoringtool.features.initialization.MenubarInitSystem;
import de.markdeuerling.monitoringtool.features.mentoring.MentoringSystem;
import de.markdeuerling.monitoringtool.features.offTime.OffTimeInitSystem;
import de.markdeuerling.monitoringtool.features.offTime.OffTimeSystem;
import de.markdeuerling.monitoringtool.features.persistence.DeserializeComponentInitSystem;
import de.markdeuerling.monitoringtool.features.persistence.SerializeComponentSystem;
import de.markdeuerling.monitoringtool.features.remove.RemoveComponentsSystem;
import de.markdeuerling.monitoringtool.features.student.ControlStudentListSystem;
import de.markdeuerling.monitoringtool.features.vizualisation.DisplayStudentOnListSystem;
import de.markdeuerling.monitoringtool.features.gui.View;
import de.markdeuerling.monitoringtool.features.initialization.RegistButtonInitSystem;
import de.markdeuerling.monitoringtool.features.student.StudentRemoveListSystem;
import de.markdeuerling.monitoringtool.features.timeManager.TimeManageSystem;
import de.markdeuerling.monitoringtool.features.vizualisation.DisplayAllAppointmentsSystem;
import de.markdeuerling.monitoringtool.features.vizualisation.DisplayAsteriskSystem;
import de.markdeuerling.monitoringtool.features.vizualisation.DisplayExpireSystem;
import de.markdeuerling.monitoringtool.features.vizualisation.DisplayNextAppointmentSystem;
import ecs.entity.EntityManager;
import ecs.event.EventManager;
import ecs.system.SystemManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Created by deuer on 16.12.2016.
 */
public class Main {

    private static boolean isRunning = true;

    public static void main(String[] args) {
        // init
        View view = new View("Exam Monitoring Tool", 1200, 720);
        EntityManager entityManager = new EntityManager(20);
        SystemManager systemManager = new SystemManager();
        EventManager<SystemEvent> eventManager = new EventManager<>();
        // features
        systemManager
                // init systems
                .add(new DeserializeComponentInitSystem(entityManager, eventManager))
                .add(new RemoveComponentsSystem(entityManager))
                .add(new RegistButtonInitSystem(entityManager, eventManager, view))
                .add(new ListViewInitSystem(view, eventManager, entityManager))
                .add(new ListViewPopupInitSystem(view, eventManager, entityManager))
                .add(new MenubarInitSystem(view, eventManager, entityManager))
                .add(new TimeManageSystem(eventManager, entityManager))
                .add(new OffTimeInitSystem(eventManager))
                // process systems
                .add(new SerializeComponentSystem(entityManager), eventManager, SystemEvent.ON_SERIALIZE)
                .add(new StudentRemoveListSystem(entityManager, view), eventManager, SystemEvent.ON_REMOVE_STUDENT)
                .add(new OffTimeSystem(entityManager), eventManager, SystemEvent.ON_OFF_TIME)
                .add(new ControlStudentListSystem(eventManager, entityManager, view), eventManager, SystemEvent.CONTROL_STUDENT)
                .add(new MentoringSystem(entityManager), eventManager, SystemEvent.TIME_UPDATE)
                .add(new DisplayStudentOnListSystem(entityManager, view), eventManager, SystemEvent.ON_ADD_STUDENT_TO_LIST)
                .add(new DisplayAsteriskSystem(entityManager, view), eventManager, SystemEvent.GRAPHIC_UPDATE)
                .add(new DisplayNextAppointmentSystem(entityManager, view), eventManager, SystemEvent.GRAPHIC_UPDATE)
                .add(new DisplayExpireSystem(entityManager, view), eventManager, SystemEvent.GRAPHIC_UPDATE)
                .add(new DisplayAllAppointmentsSystem(entityManager, view), eventManager, SystemEvent.GRAPHIC_UPDATE)
                // update systems
//                .add(new DebugSystem(entityManager))
                .startup();
        view.getFrame().setVisible(true);
        onCloseWindow(view, systemManager);
//        update(systemManager);
    }

    private static void update(SystemManager system) {
        while (isRunning) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            system.update();
        }
    }

    private static void onCloseWindow(View view, SystemManager system) {
        view.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                system.shutdown();
                isRunning = false;
            }
        });
    }
}