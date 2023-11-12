package org.group2.iatic.ihm.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.group2.iatic.ihm.actions.BookingActions;
import org.group2.iatic.ihm.models.BookingDataModel;
import org.group2.iatic.ihm.models.PersonDataModel;
import org.group2.iatic.ihm.models.RoomDataModel;

import java.util.List;

public class BookingsController {

    BookingActions bookingActions;

    public List<RoomDataModel> roomDataModels;
    public List<PersonDataModel> personDataModels;
    public List<BookingDataModel> bookingDataModels;

    public BooleanProperty bookingsUpdates = new SimpleBooleanProperty(true);

    public BookingsController(BookingActions bookingActions) {
        this.bookingActions = bookingActions;
        this.roomDataModels = bookingActions.getRooms();
        this.personDataModels = bookingActions.getPersons();
        this.bookingDataModels = bookingActions.getBookings();
        bookingActions.bookingUpdates.addListener((observableValue, aBoolean, t1) -> {
            this.roomDataModels = bookingActions.getRooms();
            this.personDataModels = bookingActions.getPersons();
            this.bookingDataModels = bookingActions.getBookings();
            bookingsUpdates.set(observableValue.getValue().booleanValue());
        });
    }

    public void onDeleteBooking(BookingDataModel bookingDataModel) {
        System.out.println("Delete person");
        bookingActions.deleteBooking(bookingDataModel);
    }

    public void onAddBooking(BookingDataModel bookingDataModel) {
        System.out.println("Add person");
        bookingActions.addBooking(bookingDataModel);
    }
}
