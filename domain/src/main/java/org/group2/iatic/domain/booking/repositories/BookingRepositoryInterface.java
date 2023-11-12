package org.group2.iatic.domain.booking.repositories;

import java.util.Map;

import org.group2.iatic.domain.booking.entities.Booking;

public interface BookingRepositoryInterface {

    /**
     * Sauvegarde la reservation dans la map et dans le fichier, la modifie si une
     * réservation de même id existe
     * 
     * @param booking la réservation à sauvegarder
     */
    void saveBooking(Booking booking);

    /**
     * Supprime la reservation de la map et du fichier
     * 
     * @param id Id de la réservation à supprimer
     */
    void deleteBooking(long id);

    /**
     * Met à jour une réservation dans la map et le fichier, ne fais rien si la
     * réservation n'existe pas
     * 
     * @param booking la réservation à mettre à jour
     */
    void updateBooking(Booking booking);

    /**
     * Récupere la map contenant toutes les réservations existantes
     * 
     * @return Map<Long, Booking>
     */
    Map<Long, Booking> getAllBookings();

    /**
     * Récupere une reservation par son id,
     * 
     * @param id Id de la réservation à récuperer
     * @return Booking
     */
    Booking getBookingById(long id);
}
