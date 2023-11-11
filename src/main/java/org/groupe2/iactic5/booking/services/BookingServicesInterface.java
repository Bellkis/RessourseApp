package org.groupe2.iactic5.booking.services;

import org.groupe2.iactic5.booking.entities.Booking;

public interface BookingServicesInterface {

   void createBooking(Booking booking);
   void deletedBooking(long id);
   void updateBooking(Booking booking);
}
