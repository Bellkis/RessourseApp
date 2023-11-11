package org.groupe2.iactic5.booking.entities;

import org.groupe2.iactic5.room.entities.Room;

public class Booking {
    private long id;
    private Room room;

    public Booking(long id, Room room) {
        this.id = id;
        this.room = room;
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

}
