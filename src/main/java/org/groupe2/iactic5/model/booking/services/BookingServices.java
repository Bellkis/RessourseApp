package org.groupe2.iactic5.model.booking.services;

import java.util.List;
import java.util.Map;

import org.groupe2.iactic5.model.booking.entities.Booking;
import org.groupe2.iactic5.model.booking.exceptions.BookingNotFoundException;
import org.groupe2.iactic5.model.booking.repositories.BookingRepositoryInterface;
import org.groupe2.iactic5.model.person.entities.Person;
import org.groupe2.iactic5.model.person.exceptions.PersonUnavailableException;
import org.groupe2.iactic5.model.room.entities.Room;
import org.groupe2.iactic5.model.room.exceptions.RoomUnavailableException;
import org.groupe2.iactic5.model.timeslot.entities.TimeSlot;
import org.groupe2.iactic5.model.timeslot.exceptions.InvalidTimeSlotException;

public class BookingServices implements BookingServicesInterface {

    private BookingRepositoryInterface bookingRepositoryInterface;

    public BookingServices(BookingRepositoryInterface bookingRepositoryInterface) {
        this.bookingRepositoryInterface = bookingRepositoryInterface;
    }

    @Override
    public void createBooking(Booking booking) {

        try {

            booking.getTimeSlot().isValid() ;
            // Valider la disponibilité de la salle
            validateRoomAvailability(booking.getRoom(), booking.getTimeSlot());

            // Valider la disponibilité de la personne
            validatePersonAvailability(booking.getPerson(), booking.getTimeSlot());

            // Tente de sauvegarder la réservation
            bookingRepositoryInterface.saveBooking(booking);

            // Si nous arrivons ici, la réservation a été créée avec succès
        } catch (RoomUnavailableException | PersonUnavailableException | InvalidTimeSlotException e)  {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    @Override
    public void createManyBookings(long id, Person person, Room room, List<TimeSlot> timeSlots) {
        // Vérifier les conditions pour chaque créneau horaire
        int i = 0;
        for (TimeSlot timeSlot : timeSlots) {

            try {
                timeSlot.isValid();

                // Valider la disponibilité de la salle
                validateRoomAvailability(room, timeSlot);

                // Valider la disponibilité de la personne
                validatePersonAvailability(person, timeSlot);

                Booking newBooking = new Booking(id + i, person, room, timeSlot);
                bookingRepositoryInterface.saveBooking(newBooking);
                i++;
            }

            catch (RoomUnavailableException | PersonUnavailableException | InvalidTimeSlotException e) {
                System.out.println("Erreur : " + e.getMessage());
                return;
            }

        }

    }

    @Override
    public void deleteBooking(long id) {
        bookingRepositoryInterface.deleteBooking(id);
    }

    @Override
    public void updateBooking(long bookingId, TimeSlot timeSlot) {
        try {
            timeSlot.isValid();
            Booking oldBooking = getBookingById(bookingId);

            // Valider la disponibilité de la salle
            validateRoomAvailability(oldBooking.getRoom(), timeSlot);

            // Valider la disponibilité de la personne
            validatePersonAvailability(oldBooking.getPerson(), timeSlot);

            // Si toutes les vérifications sont réussies, mettre à jour la réservation
            Booking booking = getBookingById(bookingId);
            booking.setTimeSlot(timeSlot);
            bookingRepositoryInterface.updateBooking(booking);

        } catch (InvalidTimeSlotException | RoomUnavailableException | PersonUnavailableException
                | BookingNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    @Override
    public Booking getBookingById(long bookingId) {

        Booking booking = bookingRepositoryInterface.getBookingById(bookingId);
        if (booking == null) {
            throw new BookingNotFoundException("Aucune réservation trouvée avec l'ID : " + bookingId);
        }
        return booking;
    }

    @Override
    public void displayBookings() {
        Map<Long, Booking> bookingsMap = bookingRepositoryInterface.getAllBookings();
        System.out.println("\nBookings Map:");
        for (Map.Entry<Long, Booking> entry : bookingsMap.entrySet()) {
            Booking booking = entry.getValue();
            System.out.println("Booking ID: " + booking.getId() +
                    ", Person ID: " + booking.getPerson().getId() +
                    ", Room ID: " + booking.getRoom().getId() +
                    ", TimeSlot ID: " + booking.getTimeSlot().getId());
        }
        System.out.println();
    }

    @Override
    public Map<Long, Booking> getAllBookings() {
        return bookingRepositoryInterface.getAllBookings();
    }

    private boolean isRoomAvailable(Room room, TimeSlot timeSlot) {

        Map<Long, Booking> bookingsMap = bookingRepositoryInterface.getAllBookings();
        // Parcourir toutes les réservations de la salle
        for (Booking booking : bookingsMap.values()) {
            // Vérifier si le créneau horaire de la réservation chevauche avec le nouveau
            // réneau
            if (booking.getRoom().equals(room) && timeSlot.overlapsWith(booking.getTimeSlot())) {
                return false; // La salle est occupée pendant ce créneau
            }
        }
        return true; // La salle est disponible pendant ce créneau
    }

    private boolean isPersonAvailable(Person person, TimeSlot timeSlot) {

        Map<Long, Booking> bookingsMap = bookingRepositoryInterface.getAllBookings();
        // Parcourir toutes les réservations de la personne
        for (Booking booking : bookingsMap.values()) {
            // Vérifier si le créneau horaire de la réservation chevauche avec le nouveau
            // créneau
            if (booking.getPerson().equals(person) && timeSlot.overlapsWith(booking.getTimeSlot())) {
                return false; // La personne est déjà réservée pendant ce créneau

            }
        }
        return true; // La personne est disponible pendant ce créneau
    }

    private void validateRoomAvailability(Room room, TimeSlot timeSlot) {
        if (!isRoomAvailable(room, timeSlot)) {
            throw new RoomUnavailableException("La salle " + room.getId() + " n'est pas disponible pendant le créneau "
                    + timeSlot.getStartTime() + " à " + timeSlot.getEndTime() + " d'ID " + timeSlot.getId());
        }
    }

    private void validatePersonAvailability(Person person, TimeSlot timeSlot) {
        if (!isPersonAvailable(person, timeSlot)) {
            throw new PersonUnavailableException(
                    "La personne " + person.getId() + " n'est pas disponible pendant le créneau "
                            + timeSlot.getStartTime() + " à " + timeSlot.getEndTime() + " d'ID " + timeSlot.getId());
        }
    }

}
