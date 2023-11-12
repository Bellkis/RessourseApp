package org.groupe2.iactic5.model.room.repositories;

import java.util.Map;

import org.groupe2.iactic5.model.room.entities.Room;

public interface RoomRepositoryInterface {

    void saveRoom(Room room);

    void deleteRoom(long roomId);

    Room getRoomById(long roomId);

    Map<Long, Room> getAllRooms();

}
