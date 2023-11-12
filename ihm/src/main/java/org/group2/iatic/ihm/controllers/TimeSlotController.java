package org.group2.iatic.ihm.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.group2.iatic.ihm.actions.TimeSlotActions;
import org.group2.iatic.ihm.models.TimeSlotDataModel;

import java.util.List;

public class TimeSlotController {

    TimeSlotActions timeSlotsActions;

    public List<TimeSlotDataModel> timeSlotDataModels;

    public BooleanProperty timeSlotsUpdates = new SimpleBooleanProperty(true);

    public TimeSlotController(TimeSlotActions timeSlotsActions) {
        this.timeSlotsActions = timeSlotsActions;
        this.timeSlotDataModels = timeSlotsActions.getTimeSlots();
        timeSlotsActions.timeSlotsUpdates.addListener((observableValue, aBoolean, t1) -> {
            this.timeSlotDataModels = timeSlotsActions.getTimeSlots();
            timeSlotsUpdates.set(observableValue.getValue().booleanValue());
        });
    }

    public void onDeleteTimeSlot(TimeSlotDataModel timeSlotDataModel) {
        timeSlotsActions.deleteTimeSlot(timeSlotDataModel);
    }

    public void onAddTimeSlot(TimeSlotDataModel timeSlotDataModel) {
        timeSlotsActions.addTimeSlot(timeSlotDataModel);
    }
}
