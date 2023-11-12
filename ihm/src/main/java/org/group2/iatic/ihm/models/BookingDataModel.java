package org.group2.iatic.ihm.models;

public class BookingDataModel {
    private long id;
    private RoomDataModel roomDataModel;
    private PersonDataModel personDataModel;
    private TimeSlotDataModel timeSlotDataModel;

    public BookingDataModel(long id, PersonDataModel personDataModel, RoomDataModel roomDataModel, TimeSlotDataModel timeSlotDataModel) {
        this.id = id;
        this.roomDataModel = roomDataModel;
        this.timeSlotDataModel = timeSlotDataModel;
        this.personDataModel = personDataModel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoomDataModel getRoom() {
        return roomDataModel;
    }

    public void setRoom(RoomDataModel roomDataModel) {
        this.roomDataModel = roomDataModel;
    }

    public PersonDataModel getPerson() {
        return personDataModel;
    }

    public void setPerson(PersonDataModel personDataModel) {
        this.personDataModel = personDataModel;
    }

    public TimeSlotDataModel getTimeSlot() {
        return timeSlotDataModel;
    }

    public void setTimeSlot(TimeSlotDataModel timeSlotDataModel) {
        this.timeSlotDataModel = timeSlotDataModel;
    }

}