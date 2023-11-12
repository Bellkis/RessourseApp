package org.group2.iatic.ihm.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.group2.iatic.ihm.actions.RoomsActions;
import org.group2.iatic.ihm.models.RoomDataModel;

import java.util.List;

public class RoomsController {

    RoomsActions roomsActions;

    public List<RoomDataModel> roomDataModels;

    public BooleanProperty roomsUpdates = new SimpleBooleanProperty(true);

    public RoomsController(RoomsActions roomsActions) {
        this.roomsActions = roomsActions;
        this.roomDataModels = roomsActions.getRooms();
        roomsActions.roomsUpdates.addListener((observableValue, aBoolean, t1) -> {
            this.roomDataModels = roomsActions.getRooms();
            roomsUpdates.set(observableValue.getValue().booleanValue());
        });
    }

    public void onDeleteRoom(RoomDataModel roomDataModel) {
        roomsActions.deleteRoom(roomDataModel);
    }

    public void onAddRoom(String name) {
        System.out.println("Add person");
        roomsActions.addRoom(name);
    }
}
