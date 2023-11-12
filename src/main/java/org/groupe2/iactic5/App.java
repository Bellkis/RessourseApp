package org.groupe2.iactic5;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.groupe2.iactic5.model.booking.entities.Booking;
import org.groupe2.iactic5.model.booking.exceptions.BookingNotFoundException;
import org.groupe2.iactic5.model.booking.repositories.BookingFileRepository;
import org.groupe2.iactic5.model.booking.services.BookingServices;
import org.groupe2.iactic5.model.booking.services.BookingServicesInterface;
import org.groupe2.iactic5.model.person.entities.Person;
import org.groupe2.iactic5.model.person.exceptions.PersonNotFoundException;
import org.groupe2.iactic5.model.person.repositories.PersonFileRepository;
import org.groupe2.iactic5.model.person.repositories.PersonRepositoryInterface;
import org.groupe2.iactic5.model.person.service.PersonServices;
import org.groupe2.iactic5.model.person.service.PersonServicesInterface;
import org.groupe2.iactic5.model.room.entities.Room;
import org.groupe2.iactic5.model.room.exceptions.RoomNotFoundException;
import org.groupe2.iactic5.model.room.repositories.RoomFileRepository;
import org.groupe2.iactic5.model.room.repositories.RoomRepositoryInterface;
import org.groupe2.iactic5.model.room.services.RoomServices;
import org.groupe2.iactic5.model.room.services.RoomServicesInterface;
import org.groupe2.iactic5.model.timeslot.entities.TimeSlot;
import org.groupe2.iactic5.model.timeslot.exceptions.TimeSlotNotFoundException;
import org.groupe2.iactic5.model.timeslot.repositories.TimeSlotFileRepository;
import org.groupe2.iactic5.model.timeslot.repositories.TimeSlotRepositoryInterface;
import org.groupe2.iactic5.model.timeslot.services.TimeSlotServices;
import org.groupe2.iactic5.model.timeslot.services.TimeSlotServicesInterface;

public class App {
        public static void main(String[] args) {

                /************************************************
                 * Test Person
                 ************************************************/
                PersonRepositoryInterface personRepositoryInterface = new PersonFileRepository();
                PersonServicesInterface personServicesInterface = new PersonServices(personRepositoryInterface);

                Person person1 = new Person(1L, "Jean");
                Person person2 = new Person(2L, "Paul");
                Person person3 = new Person(3L, "Marie");
                Person person4 = new Person(4L, "Rabbit");

                personServicesInterface.displayPersons();

                personServicesInterface.createPerson(person1);
                personServicesInterface.createPerson(person2);
                personServicesInterface.createPerson(person3);
                personServicesInterface.createPerson(person4);

                personServicesInterface.displayPersons();

                personServicesInterface.deletePerson(4);

                personServicesInterface.displayPersons();

                try {

                        personServicesInterface.getPersonById(4);

                } catch (PersonNotFoundException e) {
                        System.err.println("Err " + e.getMessage());
                }

                System.out.println("**************************************************");

                // /************************************************
                //  * Test Room
                //  ************************************************/

                RoomRepositoryInterface roomRepositoryInterface = new RoomFileRepository();
                RoomServicesInterface roomServicesInterface = new RoomServices(roomRepositoryInterface);

                // Créer de nouvelles chambres
                Room room1 = new Room(1, "RoomTest 1");
                Room room2 = new Room(2, "RoomTest 2");
                Room room3 = new Room(3, "RoomTest 3");
                Room room4 = new Room(4, "RoomTest 4");

                roomServicesInterface.displayRooms();

                roomServicesInterface.createRoom(room1);
                roomServicesInterface.createRoom(room2);
                roomServicesInterface.createRoom(room3);
                roomServicesInterface.createRoom(room4);

                roomServicesInterface.displayRooms();

                roomServicesInterface.deleteRoom(4);

                roomServicesInterface.displayRooms();

                try {

                        roomServicesInterface.getRoomById(4);

                } catch (RoomNotFoundException e) {
                        System.err.println("Err " + e.getMessage());
                }

                System.out.println("**************************************************");

                // /************************************************
                //  * Test TimeSlot
                //  ************************************************/

                // Utiliser une implémentation de repository pour TimeSlot
                TimeSlotRepositoryInterface timeSlotRepositoryInterface = new TimeSlotFileRepository();
                TimeSlotServicesInterface timeSlotServicesInterface = new TimeSlotServices(timeSlotRepositoryInterface);

                timeSlotServicesInterface.displayTimeSlots();

                // Création d'instances de TimeSlot
                TimeSlot timeSlot1 = new TimeSlot(1L, LocalDateTime.of(2023, 11, 15, 10, 0),
                                LocalDateTime.of(2023, 11, 15, 12, 0));
                TimeSlot timeSlot2 = new TimeSlot(2L, LocalDateTime.of(2023, 11, 16, 14, 30),
                                LocalDateTime.of(2023, 11, 16, 17, 30));
                TimeSlot timeSlot3 = new TimeSlot(3L, LocalDateTime.of(2023, 11, 17, 15, 30),
                                LocalDateTime.of(2023, 11, 17, 18, 30));
                TimeSlot timeSlot4 = new TimeSlot(4L, LocalDateTime.of(2023, 11, 18, 17, 30),
                                LocalDateTime.of(2023, 11, 15, 20, 30));

                // Ajout des TimeSlots à la repository
                timeSlotServicesInterface.createTimeSlot(timeSlot1);
                timeSlotServicesInterface.createTimeSlot(timeSlot2);
                timeSlotServicesInterface.createTimeSlot(timeSlot3);
                timeSlotServicesInterface.createTimeSlot(timeSlot4);

                timeSlotServicesInterface.displayTimeSlots();

                timeSlotServicesInterface.deleteTimeSlot(4);

                timeSlotServicesInterface.displayTimeSlots();

                try {

                        timeSlotServicesInterface.getTimeSlotById(3);

                } catch (TimeSlotNotFoundException e) {
                        System.err.println("Err " + e.getMessage());
                }

                System.out.println("**************************************************");

                // /************************************************
                //  * Test Booking
                //  ************************************************/

                BookingServicesInterface bookingServicesInterface = new BookingServices(
                                new BookingFileRepository(personServicesInterface, roomServicesInterface,
                                                timeSlotServicesInterface));

                // Création d'une instance de Booking
                Booking booking1 = new Booking(1L, person2, room3, timeSlot1);
                Booking booking2 = new Booking(2L, person3, room1, timeSlot2);
                Booking booking3 = new Booking(3L, person1, room2, timeSlot3);

                bookingServicesInterface.displayBookings();

                bookingServicesInterface.createBooking(booking1);
                bookingServicesInterface.createBooking(booking2);
                bookingServicesInterface.createBooking(booking3);

                bookingServicesInterface.displayBookings();

                TimeSlot timeSlot5 = new TimeSlot(5L, LocalDateTime.of(2023, 11, 20, 13, 0),
                                LocalDateTime.of(2023, 11, 21, 16, 0));

                bookingServicesInterface.updateBooking(1, timeSlot5);

                bookingServicesInterface.displayBookings();

                bookingServicesInterface.deleteBooking(3);

                bookingServicesInterface.displayBookings();

                TimeSlot timeSlot6 = new TimeSlot(6L, LocalDateTime.of(2023, 11, 21, 11, 0),
                                LocalDateTime.of(2023, 11, 22, 15, 0));

                Booking booking4 = new Booking(4L, person2, room2, timeSlot6);
                bookingServicesInterface.createBooking(booking4);

                bookingServicesInterface.displayBookings();

                try {
                        bookingServicesInterface.getBookingById(3);
                } catch (BookingNotFoundException e) {
                        System.err.println("Err " + e.getMessage());
                }

                bookingServicesInterface.createManyBookings(4, person1, room4,
                                Arrays.asList(timeSlot1, timeSlot2, timeSlot3));

                bookingServicesInterface.displayBookings();

        }

}
