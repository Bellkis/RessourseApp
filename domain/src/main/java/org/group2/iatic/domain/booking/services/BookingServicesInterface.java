package org.group2.iatic.domain.booking.services;

import org.group2.iatic.domain.booking.entities.Booking;
import org.group2.iatic.domain.person.entities.Person;
import java.util.Map;

import org.group2.iatic.domain.room.entities.Room;
import org.group2.iatic.domain.timeslot.entities.TimeSlot;

import java.util.List;

public interface BookingServicesInterface {

   /**
    * Crée une nouvelle réservation. la modifie si une réservation de même id
    * existe
    * 
    * @param booking
    */
   void createBooking(Booking booking);

   /**
    * Créer des réservations sur un ensemble de créneaux.
    * 
    * @param id        Id de la réservation à créer
    * @param person    Person lier à la réservation
    * @param room      Salle reservé
    * @param timeSlots Ensemble de créneaux à créer pour cette réservation
    */
   void createManyBookings(long id, Person person, Room room, List<TimeSlot> timeSlots);

   /**
    * Supprime une réservation, ne fait rien si la réservation n'existe pas
    * 
    * @param id Id de la reservation
    */
   void deleteBooking(long id);

   /**
    * Change une réservation de créneau temporel
    * 
    * @param bookingId Id de la réservation
    * @param timeSlot  nouveau créneau de la réservation
    */
   void updateBooking(long bookingId, TimeSlot timeSlot);

   /**
    * Recupère une réservation par son ID. retourne null si la réservation n'existe
    * pas
    * 
    * @param id L'ID de la réservation à récupérer
    * @return Booking
    */
   Booking getBookingById(long id);

   /**
    * Récupere la map contenant toutes les réservations existantes
    * 
    * @return Map<Long, Booking>
    */
   Map<Long, Booking> getAllBookings();

   /**
    * achiche les reservations existantes
    */
   void displayBookings();

}
