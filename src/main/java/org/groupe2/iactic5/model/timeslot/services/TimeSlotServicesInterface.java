package org.groupe2.iactic5.model.timeslot.services;

import org.groupe2.iactic5.model.timeslot.entities.TimeSlot;

public interface TimeSlotServicesInterface {

    void createTimeSlot(TimeSlot timeSlot);

    void deleteTimeSlot(long timeSlotid);

    TimeSlot getTimeSlotById(long timeSlotId);

    void displayTimeSlots();
}
