package org.groupe2.iactic5.timeslot.services;

import java.util.Map;

import org.groupe2.iactic5.timeslot.entities.TimeSlot;
import org.groupe2.iactic5.timeslot.repositories.TimeSlotRepositoryInterface;

public class TimeSlotServices implements TimeSlotServicesInterface {

    private TimeSlotRepositoryInterface timeSlotRepositoryInterface;

    public TimeSlotServices(TimeSlotRepositoryInterface timeSlotServicesInterface) {
        this.timeSlotRepositoryInterface = timeSlotServicesInterface;
    }

    @Override
    public void createTimeSlot(TimeSlot timeSlot) {
        timeSlotRepositoryInterface.saveTimeSlot(timeSlot);
    }

    @Override
    public void deleteTimeSlot(long timeSlotid) {
        timeSlotRepositoryInterface.deleteTimeSlot(timeSlotid);
    }

    @Override
    public TimeSlot getTimeSlotById(long timeSlotId) {
        return timeSlotRepositoryInterface.getTimeSlotByID(timeSlotId);
    }

    @Override
    public void displayTimeSlots() {
        Map<Long, TimeSlot> timeSlotsMap = timeSlotRepositoryInterface.getAllTimeSlots();
        System.out.println("TimeSlots Map:");
        for (Map.Entry<Long, TimeSlot> entry : timeSlotsMap.entrySet()) {
            TimeSlot timeSlot = entry.getValue();
            System.out.println("TimeSlot ID: " + timeSlot.getId() +
                    ", StartTime: " + timeSlot.getStartTime() +
                    ", EndTime: " + timeSlot.getEndTime());
        }
        System.out.println();
    }

}
