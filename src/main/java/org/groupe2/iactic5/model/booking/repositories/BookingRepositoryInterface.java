package org.groupe2.iactic5.model.booking.repositories;

import java.util.Map;

import org.groupe2.iactic5.model.booking.entities.Booking;

public interface BookingRepositoryInterface {

    void saveBooking(Booking booking);

    void deleteBooking(long id);

    void updateBooking(Booking booking);

    Map<Long, Booking> getAllBookings();

    Booking getBookingById(long id);
}
