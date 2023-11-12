package org.groupe2.iactic5.model.views_actions;

import org.group2.iatic.domain.timeslot.entities.TimeSlot;
import org.group2.iatic.domain.timeslot.repositories.TimeSlotRepositoryInterface;
import org.group2.iatic.ihm.actions.TimeSlotActions;
import org.group2.iatic.ihm.models.TimeSlotDataModel;

import java.util.List;
import java.util.stream.Collectors;

public class TimeSlotActionsImpl implements TimeSlotActions {

    TimeSlotRepositoryInterface repositoryInterface;

    public TimeSlotActionsImpl(TimeSlotRepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void addTimeSlot(TimeSlotDataModel timeSlotDataModel) {
        repositoryInterface.saveTimeSlot(new TimeSlot(timeSlotDataModel.getId(), timeSlotDataModel.getStartTime(), timeSlotDataModel.getEndTime()));
        timeSlotsUpdates.set(!timeSlotsUpdates.get());
    }

    @Override
    public void deleteTimeSlot(TimeSlotDataModel timeSlotDataModel) {
        repositoryInterface.deleteTimeSlot(timeSlotDataModel.getId());
        timeSlotsUpdates.set(!timeSlotsUpdates.get());
    }

    @Override
    public List<TimeSlotDataModel> getTimeSlots() {
        return repositoryInterface.getAllTimeSlots().values().stream().map(TimeSlotActionsImpl::fromTimeSlot).collect(Collectors.toList());
    }

    public static TimeSlotDataModel fromTimeSlot(TimeSlot timeSlot) {
        return new TimeSlotDataModel(timeSlot.getId(), timeSlot.getStartTime(), timeSlot.getEndTime());
    }
}
