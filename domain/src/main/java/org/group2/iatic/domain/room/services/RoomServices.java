package org.group2.iatic.domain.room.services;

import java.util.Map;

import org.group2.iatic.domain.room.entities.Room;
import org.group2.iatic.domain.room.exceptions.RoomNotFoundException;
import org.group2.iatic.domain.room.repositories.RoomRepositoryInterface;

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
        Room room = roomRepositoryInterface.getRoomById(roomId);

        if (room == null) {
            throw new RoomNotFoundException("Aucune salle trouvée avec l'ID : " + roomId);
        }

        return room;
    }

    @Override
    public void displayRooms() {
        Map<Long, Room> roomsMap = roomRepositoryInterface.getAllRooms();
        System.out.println("\nRooms Map:");
        for (Map.Entry<Long, Room> entry : roomsMap.entrySet()) {
            Room room = entry.getValue();
            System.out.println("Room ID: " + room.getId() + ", Name: " + room.getName());
        }
        System.out.println();

    }

    @Override
    public Map<Long, Room> getAllRooms() {
        return roomRepositoryInterface.getAllRooms();
    }
}
