package org.group2.iatic.domain.booking.entities;

import org.group2.iatic.domain.person.entities.Person;
import org.group2.iatic.domain.room.entities.Room;
import org.group2.iatic.domain.timeslot.entities.TimeSlot;

public class Booking {
    private long id;
    private Room room;
    private Person person;
    private TimeSlot timeSlot;

    public Booking(long id, Person person, Room room, TimeSlot timeSlot) {
        this.id = id;
        this.room = room;
        this.timeSlot = timeSlot;
        this.person = person;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

}
