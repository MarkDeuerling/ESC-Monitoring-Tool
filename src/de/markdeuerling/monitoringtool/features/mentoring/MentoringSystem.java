package de.markdeuerling.monitoringtool.features.mentoring;

import de.markdeuerling.monitoringtool.features.appointments.NextAppointmentIn24Hours;
import de.markdeuerling.monitoringtool.features.student.Identity;
import de.markdeuerling.monitoringtool.features.timeManager.TimeFormat;
import ecs.entity.Entity;
import ecs.entity.EntityManager;
import ecs.system.IProcessSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by deuer on 02.02.2017.
 */
public class MentoringSystem implements IProcessSystem {

    private EntityManager entityManager;

    public MentoringSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
        // TIME_UPDATE
    }

    @Override
    public void onProcess(Entity entity, Object data) {
        int dayMinute = 24 * 60;
        Entity timeEntity = entityManager.findEntity(TimeFormat.class);
        if (timeEntity == null) {
            return;
        }
        TimeFormat timeFormat = entityManager.getComponent(timeEntity, TimeFormat.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat.format);
        LocalDateTime currentTime = LocalDateTime.now();
        entityManager
                .findEntities(Identity.class, Mentoring.class)
                .forEach(e -> {
                    Mentoring mentoring = entityManager.getComponent(e, Mentoring.class);
                    LocalDateTime mentoringDate = LocalDateTime.parse(mentoring.nextTalk, formatter);
                    long hoursBetween = ChronoUnit.MINUTES.between(currentTime, mentoringDate);
                    entityManager.removeComponent(e, NextAppointmentIn24Hours.class);
                    // is meeting in one day
                    if (hoursBetween <= dayMinute && hoursBetween > 0) {
                        entityManager.addComponent(e, NextAppointmentIn24Hours.class, new NextAppointmentIn24Hours());
                    }
                });
    }
}
