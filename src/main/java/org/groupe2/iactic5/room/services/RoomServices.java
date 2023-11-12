package org.groupe2.iactic5.room.services;

import java.util.Map;

import org.groupe2.iactic5.room.entities.Room;
import org.groupe2.iactic5.room.repositories.RoomRepositoryInterface;

public class RoomServices implements RoomServicesInterface {

    private RoomRepositoryInterface roomRepositoryInterface;

    public RoomServices(RoomRepositoryInterface roomRepositoryInterface) {
        this.roomRepositoryInterface = roomRepositoryInterface;
    }

    @Override
    public void createRoom(Room room) {
        roomRepositoryInterface.saveRoom(room);
    }

    @Override
    public void deleteRoom(long roomId) {
        roomRepositoryInterface.deleteRoom(roomId);
    }

    @Override
    public Room getRoomById(long roomId) {
        return roomRepositoryInterface.getRoomById(roomId);
    }

    @Override
    public void displayRooms() {
        Map<Long, Room> roomsMap = roomRepositoryInterface.getAllRooms();
        System.out.println("Rooms Map:");
        for (Map.Entry<Long, Room> entry : roomsMap.entrySet()) {
            Room room = entry.getValue();
            System.out.println("Room ID: " + room.getId() + ", Name: " + room.getName());
        }
        System.out.println();
        
    }
}
