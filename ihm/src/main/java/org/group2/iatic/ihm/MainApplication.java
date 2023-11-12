package org.group2.iatic.ihm;

import org.group2.iatic.ihm.actions.BookingActions;
import org.group2.iatic.ihm.actions.GuestsActions;
import org.group2.iatic.ihm.actions.RoomsActions;
import org.group2.iatic.ihm.actions.TimeSlotActions;
import org.group2.iatic.ihm.models.BookingDataModel;
import org.group2.iatic.ihm.models.PersonDataModel;
import org.group2.iatic.ihm.models.RoomDataModel;
import org.group2.iatic.ihm.models.TimeSlotDataModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.group2.iatic.ihm.views.HomeView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(new HomeView(new GuestsActions() {
            @Override
            public void addGuest(String name) {
                System.out.println("Called added guest");
            }

            @Override
            public void deleteGuest(PersonDataModel person) {
                System.out.println("Called delete");
            }

            @Override
            public List<PersonDataModel> getGuests() {
                List<PersonDataModel> fakePeople = new ArrayList<>();

                fakePeople.add(new PersonDataModel(1, "John Doe"));
                fakePeople.add(new PersonDataModel(2, "Jane Smith"));
                fakePeople.add(new PersonDataModel(3, "Bob Johnson"));
                fakePeople.add(new PersonDataModel(4, "Alice Williams"));
                fakePeople.add(new PersonDataModel(5, "Charlie Brown"));

                return fakePeople;
            }
        }, new RoomsActions() {
            @Override
            public void addRoom(String name) {
                System.out.println("Add room");
            }

            @Override
            public void deleteRoom(RoomDataModel room) {
                System.out.println("Delete room");

            }

            @Override
            public List<RoomDataModel> getRooms() {
                List<RoomDataModel> fakeRoomDataModels = new ArrayList<>();

                fakeRoomDataModels.add(new RoomDataModel(101, "Living Room"));
                fakeRoomDataModels.add(new RoomDataModel(102, "Bedroom"));
                fakeRoomDataModels.add(new RoomDataModel(103, "Kitchen"));

                return fakeRoomDataModels;
            }
        }, new BookingActions() {
            @Override
            public void addBooking(BookingDataModel name) {
                System.out.println("Add booking");
            }

            @Override
            public void deleteBooking(BookingDataModel booking) {
                System.out.println("Delete booking");

            }

            @Override
            public List<RoomDataModel> getRooms() {
                List<RoomDataModel> fakeRoomDataModels = new ArrayList<>();

                fakeRoomDataModels.add(new RoomDataModel(101, "Living Room"));
                fakeRoomDataModels.add(new RoomDataModel(102, "Bedroom"));
                fakeRoomDataModels.add(new RoomDataModel(103, "Kitchen"));

                return fakeRoomDataModels;
            }

            @Override
            public List<BookingDataModel> getBookings() {
                List<BookingDataModel> bookingDataModels = new ArrayList<>();

                for (int i = 0; i < 10; i++) {

                    RoomDataModel roomDataModel = new RoomDataModel(i + 1, "Room " + (i + 1));
                    roomDataModel.setId(i + 1);
                    roomDataModel.setName("Room " + (i + 1));

                    PersonDataModel personDataModel = new PersonDataModel(i + 1, "Person " + (i + 1));

                    TimeSlotDataModel timeSlotDataModel = new TimeSlotDataModel(i + 1, LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(i).plusHours(2));

                    bookingDataModels.add(new BookingDataModel(i, personDataModel, roomDataModel, timeSlotDataModel));
                }

                return bookingDataModels;
            }

            @Override
            public List<PersonDataModel> getPersons() {
                List<PersonDataModel> fakePeople = new ArrayList<>();

                fakePeople.add(new PersonDataModel(1, "John Doe"));
                fakePeople.add(new PersonDataModel(2, "Jane Smith"));
                fakePeople.add(new PersonDataModel(3, "Bob Johnson"));
                fakePeople.add(new PersonDataModel(4, "Alice Williams"));
                fakePeople.add(new PersonDataModel(5, "Charlie Brown"));

                return fakePeople;
            }
        }, new TimeSlotActions() {
            @Override
            public void addTimeSlot(TimeSlotDataModel name) {

            }

            @Override
            public void deleteTimeSlot(TimeSlotDataModel timeSlot) {

            }

            @Override
            public List<TimeSlotDataModel> getTimeSlots() {
                List<TimeSlotDataModel> timeSlotDataModels = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    TimeSlotDataModel timeSlotDataModel = new TimeSlotDataModel(i + 1, LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(i).plusHours(2));
                    timeSlotDataModels.add(timeSlotDataModel);
                }

                return timeSlotDataModels;
            }
        }));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }


}