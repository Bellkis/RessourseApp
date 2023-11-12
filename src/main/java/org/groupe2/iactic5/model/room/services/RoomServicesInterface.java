package org.groupe2.iactic5.model.room.services;

import org.groupe2.iactic5.model.room.entities.Room;

public interface RoomServicesInterface {

    void createRoom(Room room);

    void deleteRoom(long roomId);

    Room getRoomById(long roomId);

    void displayRooms();

}