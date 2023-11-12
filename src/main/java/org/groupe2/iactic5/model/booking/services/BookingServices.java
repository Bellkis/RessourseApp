package org.groupe2.iactic5.model.booking.services;

import java.util.List;
import java.util.Map;

import org.groupe2.iactic5.model.booking.entities.Booking;
import org.groupe2.iactic5.model.booking.repositories.BookingRepositoryInterface;
import org.groupe2.iactic5.model.person.entities.Person;
import org.groupe2.iactic5.model.room.entities.Room;
import org.groupe2.iactic5.model.timeslot.entities.TimeSlot;

public class BookingServices implements BookingServicesInterface {

    private BookingRepositoryInterface bookingRepositoryInterface;

    public BookingServices(BookingRepositoryInterface bookingRepositoryInterface) {
        this.bookingRepositoryInterface = bookingRepositoryInterface;
    }

    @Override
    public void createBooking(Booking booking) {
        // Vérifier si la salle est disponible pendant le créneau
        if (this.isRoomAvailable(booking.getRoom(), booking.getTimeSlot())) {
            // Vérifier si la personne est disponible pendant le créneau
            if (this.isPersonAvailable(booking.getPerson(), booking.getTimeSlot())) {
                // Créer la réservation
                bookingRepositoryInterface.saveBooking(booking);
            } else {
                System.out.println("La personne n'est pas disponible pendant ce créneau.");
            }
        } else {
            System.out.println("La salle n'est pas disponible pendant ce créneau.");
        }

    }

    @Override
    public void createManyBookings(long id, Person person, Room room, List<TimeSlot> timeSlots) {
        // Vérifier les conditions pour chaque créneau horaire
        for (TimeSlot timeSlot : timeSlots) {
            // Vérifier si la salle est disponible pendant le créneau
            if (!this.isRoomAvailable(room, timeSlot)) {
                System.out.println("La salle n'est pas disponible pendant le créneau " + timeSlot);
                return; // Arrêter la création de réservation
            }

            // Vérifier si la personne est disponible pendant le créneau
            if (!this.isPersonAvailable(person, timeSlot)) {
                System.out.println("La personne n'est pas disponible pendant le créneau " + timeSlot);
                return; // Arrêter la création de réservation
            }
        }

        // Si toutes les vérifications sont réussies, créer la réservation
        for (TimeSlot timeSlot : timeSlots) {
            Booking newBooking = new Booking(id, person, room, timeSlot);
            bookingRepositoryInterface.saveBooking(newBooking);
            // Vous pouvez également sauvegarder les réservations dans un fichier ou une
            // base de données si nécessaire
            System.out.println("Réservation créée avec succès : " + newBooking);
        }

    }

    @Override
    public void deleteBooking(long id) {
        bookingRepositoryInterface.deleteBooking(id);
    }

    @Override
    public void updateBooking(long bookingId, TimeSlot timeSlot) {
        // Vérifier si la salle est disponible pendant le créneau
        Booking oldBooking = getBookingById(bookingId);
        if (this.isRoomAvailable(oldBooking.getRoom(), oldBooking.getTimeSlot())) {
            // Vérifier si la personne est disponible pendant le créneau
            if (this.isPersonAvailable(oldBooking.getPerson(), oldBooking.getTimeSlot())) {
                // Update la réservation
                Booking booking = getBookingById(bookingId);
                booking.setTimeSlot(timeSlot);
                bookingRepositoryInterface.updateBooking(booking);
                
            } else {
                System.out.println("La personne n'est pas disponible pendant ce créneau.");
            }
        } else {
            System.out.println("La salle n'est pas disponible pendant ce créneau.");
        }
    }

    @Override
    public Booking getBookingById(long bookingId) {
        return bookingRepositoryInterface.getBookingById(bookingId);
    }

    @Override
    public void displayBookings() {
        Map<Long, Booking> bookingsMap = bookingRepositoryInterface.getAllBookings();
        System.out.println("Bookings Map:");
        for (Map.Entry<Long, Booking> entry : bookingsMap.entrySet()) {
            Booking booking = entry.getValue();
            System.out.println("Booking ID: " + booking.getId() +
                    ", Person ID: " + booking.getPerson().getId() +
                    ", Room ID: " + booking.getRoom().getId() +
                    ", TimeSlot ID: " + booking.getTimeSlot().getId());
        }
        System.out.println();
    }

    public boolean isRoomAvailable(Room room, TimeSlot timeSlot) {

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

    public boolean isPersonAvailable(Person person, TimeSlot timeSlot) {

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

}
