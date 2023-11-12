package org.groupe2.iactic5.model.timeslot.services;

import java.util.Map;

import org.groupe2.iactic5.model.timeslot.entities.TimeSlot;
import org.groupe2.iactic5.model.timeslot.exceptions.InvalidTimeSlotException;
import org.groupe2.iactic5.model.timeslot.exceptions.TimeSlotNotFoundException;
import org.groupe2.iactic5.model.timeslot.repositories.TimeSlotRepositoryInterface;

public class TimeSlotServices implements TimeSlotServicesInterface {

    private TimeSlotRepositoryInterface timeSlotRepositoryInterface;

    public TimeSlotServices(TimeSlotRepositoryInterface timeSlotServicesInterface) {
        this.timeSlotRepositoryInterface = timeSlotServicesInterface;
    }

    @Override
    public void createTimeSlot(TimeSlot timeSlot) {
        try {
            // Tente d'enregistrer le créneau temporel
            timeSlot.isValid();
            timeSlotRepositoryInterface.saveTimeSlot(timeSlot);

            // Si nous arrivons ici, le créneau temporel est valide
        } catch (InvalidTimeSlotException e) {
            // Capture l'exception et affiche un message d'erreur
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    @Override
    public void deleteTimeSlot(long timeSlotid) {
        timeSlotRepositoryInterface.deleteTimeSlot(timeSlotid);
    }

    @Override
    public TimeSlot getTimeSlotById(long timeSlotId) {
        TimeSlot timeSlot = timeSlotRepositoryInterface.getTimeSlotByID(timeSlotId);

        if (timeSlot == null) {
            throw new TimeSlotNotFoundException("Aucun créneau trouvé avec l'ID : " + timeSlotId);
        }

        return timeSlot;
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

    @Override
    public Map<Long, TimeSlot> getAllTimeSlots() {
        return timeSlotRepositoryInterface.getAllTimeSlots();
    }

}
