package org.groupe2.iactic5.model.views_actions;

import org.group2.iatic.domain.booking.entities.Booking;
import org.group2.iatic.domain.booking.repositories.BookingRepositoryInterface;
import org.group2.iatic.domain.person.entities.Person;
import org.group2.iatic.domain.person.repositories.PersonRepositoryInterface;
import org.group2.iatic.domain.room.entities.Room;
import org.group2.iatic.domain.room.repositories.RoomRepositoryInterface;
import org.group2.iatic.domain.timeslot.entities.TimeSlot;
import org.group2.iatic.ihm.actions.BookingActions;
import org.group2.iatic.ihm.models.BookingDataModel;
import org.group2.iatic.ihm.models.PersonDataModel;
import org.group2.iatic.ihm.models.RoomDataModel;
import org.group2.iatic.ihm.models.TimeSlotDataModel;

import java.util.List;
import java.util.stream.Collectors;

public class BookingsActionsImpl implements BookingActions {

    BookingRepositoryInterface bookingRepository;
    PersonRepositoryInterface personRepository;
    RoomRepositoryInterface roomRepository;

    public BookingsActionsImpl(BookingRepositoryInterface bookingRepository, PersonRepositoryInterface personRepository, RoomRepositoryInterface roomRepository) {
        this.bookingRepository = bookingRepository;
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void addBooking(BookingDataModel bookingDataModel) {
        Person person = new Person((long) bookingDataModel.getPerson().getId(), bookingDataModel.getPerson().getName());
        Room room = new Room(bookingDataModel.getRoom().getId(), bookingDataModel.getRoom().getName());
        TimeSlot timeSlot = new TimeSlot(bookingDataModel.getTimeSlot().getId(), bookingDataModel.getTimeSlot().getStartTime(), bookingDataModel.getTimeSlot().getEndTime());
        bookingRepository.saveBooking(new Booking(bookingDataModel.getId(), person, room, timeSlot));
        bookingUpdates.set(!bookingUpdates.get());
    }

    @Override
    public void deleteBooking(BookingDataModel bookingDataModel) {
        bookingRepository.deleteBooking(bookingDataModel.getId());
        bookingUpdates.set(!bookingUpdates.get());
    }

    @Override
    public List<RoomDataModel> getRooms() {
        return roomRepository.getAllRooms().values().stream().map(BookingsActionsImpl::fromRoom).collect(Collectors.toList());
    }

    @Override
    public List<BookingDataModel> getBookings() {
        return bookingRepository.getAllBookings().values().stream().map(BookingsActionsImpl::fromBooking).collect(Collectors.toList());
    }

    @Override
    public List<PersonDataModel> getPersons() {
        return personRepository.getAllPersons().values().stream().map(BookingsActionsImpl::fromPerson).collect(Collectors.toList());
    }

    public static BookingDataModel fromBooking(Booking booking) {
        return new BookingDataModel((int) booking.getId(), fromPerson(booking.getPerson()), fromRoom(booking.getRoom()), fromTimeSlot(booking.getTimeSlot()));
    }

    public static RoomDataModel fromRoom(Room room) {
        return new RoomDataModel((int) room.getId(), room.getName());
    }

    public static PersonDataModel fromPerson(Person person) {
        return new PersonDataModel(person.getId().intValue(), person.getName());
    }

    public static TimeSlotDataModel fromTimeSlot(TimeSlot timeSlot) {
        return new TimeSlotDataModel(timeSlot.getId(), timeSlot.getStartTime(), timeSlot.getEndTime());
    }
}
