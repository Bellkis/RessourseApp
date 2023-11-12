package org.groupe2.iactic5.model.views_actions;

import org.group2.iatic.domain.room.entities.Room;
import org.group2.iatic.domain.room.repositories.RoomRepositoryInterface;
import org.group2.iatic.ihm.actions.RoomsActions;
import org.group2.iatic.ihm.models.RoomDataModel;
import org.group2.iatic.ihm.utils.Generators;

import java.util.List;
import java.util.stream.Collectors;

public class RoomsActionsImpl implements RoomsActions {

    RoomRepositoryInterface repositoryInterface;

    public RoomsActionsImpl(RoomRepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void addRoom(String s) {
        repositoryInterface.saveRoom(new Room(Generators.generateRandomIntBasedOnTime(), s));
        roomsUpdates.set(!roomsUpdates.get());
    }

    @Override
    public void deleteRoom(RoomDataModel roomDataModel) {
        repositoryInterface.deleteRoom(roomDataModel.getId());
        roomsUpdates.set(!roomsUpdates.get());
    }

    @Override
    public List<RoomDataModel> getRooms() {
        return repositoryInterface.getAllRooms().values().stream().map(RoomsActionsImpl::fromRoom).collect(Collectors.toList());
    }

    public static RoomDataModel fromRoom(Room room) {
        return new RoomDataModel((int)room.getId(), room.getName());
    }
}
