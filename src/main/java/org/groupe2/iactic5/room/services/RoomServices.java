package org.groupe2.iactic5.room.services;

import org.groupe2.iactic5.room.entities.Room;
import org.groupe2.iactic5.room.repositories.RoomRepositoryInterface;

public class RoomServices implements RoomServicesInterface {

    private RoomRepositoryInterface roomRepositoryInterface;

    public RoomServices(RoomRepositoryInterface roomRepositoryInterface) {
        this.roomRepositoryInterface = roomRepositoryInterface;
    }

    // Setter pour l'injection de dépendances
    public void setRoomRepository(RoomRepositoryInterface roomRepositoryInterface) {
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
    public void getRoomById(long roomId) {
        roomRepositoryInterface.getRoomById(roomId);
    }
    
    
}
