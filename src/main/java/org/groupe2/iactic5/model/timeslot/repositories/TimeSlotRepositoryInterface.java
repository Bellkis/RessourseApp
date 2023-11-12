package org.groupe2.iactic5.model.timeslot.repositories;

import java.util.Map;

import org.groupe2.iactic5.model.timeslot.entities.TimeSlot;

public interface TimeSlotRepositoryInterface {
    void saveTimeSlot(TimeSlot timeSlot);

    void deleteTimeSlot(long timeSlotId);

    TimeSlot getTimeSlotByID(long timeSlotId);

    Map<Long, TimeSlot> getAllTimeSlots();

}
