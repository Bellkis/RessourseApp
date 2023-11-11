package org.groupe2.iactic5.booking.repositories;

import java.util.List;

import org.groupe2.iactic5.booking.entities.Booking;

public interface BookingRepositoryInterface {
    List<Booking> getAllBookings();
    Booking getBookingById(long id);
    void saveBooking(Booking booking);
    void deleteBooking(long id);
    void updateBooking(Booking booking);
}
