package org.groupe2.iactic5.room.services;

import org.groupe2.iactic5.room.entities.Room;

public interface RoomServicesInterface {

    void createRoom(Room room);
    void deleteRoom(long roomId);
    void getRoomById(long roomId);

}