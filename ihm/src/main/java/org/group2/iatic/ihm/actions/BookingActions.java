package org.group2.iatic.ihm.actions;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.group2.iatic.ihm.models.BookingDataModel;
import org.group2.iatic.ihm.models.PersonDataModel;
import org.group2.iatic.ihm.models.RoomDataModel;

import java.util.List;

public interface BookingActions {

    void addBooking(BookingDataModel name);

    void deleteBooking(BookingDataModel bookingDataModel);

    List<RoomDataModel> getRooms();

    List<BookingDataModel> getBookings();

    List<PersonDataModel> getPersons();

    BooleanProperty bookingUpdates = new SimpleBooleanProperty(true);
}
