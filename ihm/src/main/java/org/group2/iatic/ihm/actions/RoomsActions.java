package org.group2.iatic.ihm.actions;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.group2.iatic.ihm.models.RoomDataModel;

import java.util.List;

public interface RoomsActions {

    void addRoom(String name);

    void deleteRoom(RoomDataModel roomDataModel);

    List<RoomDataModel> getRooms();

    BooleanProperty roomsUpdates = new SimpleBooleanProperty(true);
}
