package org.groupe2.iactic5.room.repositories;

import java.util.Map;

import org.groupe2.iactic5.room.entities.Room;

public interface RoomRepositoryInterface {

    void saveRoom(Room room);

    void deleteRoom(long roomId);

    Room getRoomById(long roomId);

    Map<Long, Room> getAllRooms();

}
