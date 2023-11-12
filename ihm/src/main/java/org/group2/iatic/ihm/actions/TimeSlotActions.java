package org.group2.iatic.ihm.actions;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.group2.iatic.ihm.models.TimeSlotDataModel;

import java.util.List;

public interface TimeSlotActions {

    void addTimeSlot(TimeSlotDataModel timeSlotDataModel);

    void deleteTimeSlot(TimeSlotDataModel timeSlotDataModel);

    List<TimeSlotDataModel> getTimeSlots();

    BooleanProperty timeSlotsUpdates = new SimpleBooleanProperty(true);
}
