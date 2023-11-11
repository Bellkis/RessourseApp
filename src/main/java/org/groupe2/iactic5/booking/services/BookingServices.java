package org.groupe2.iactic5.booking.services;

import org.groupe2.iactic5.booking.entities.Booking;
import org.groupe2.iactic5.booking.repositories.BookingRepositoryInterface;

public class BookingServices implements BookingServicesInterface {

    private BookingRepositoryInterface bookingRepositoryInterface;

     public BookingServices(BookingRepositoryInterface bookingRepositoryInterface) {
        this.bookingRepositoryInterface = bookingRepositoryInterface;
    }

    @Override
    public void createBooking(Booking booking) {
        bookingRepositoryInterface.saveBooking(booking);
    }

    @Override
    public void deletedBooking(long id) {
        bookingRepositoryInterface.deleteBooking(id);
    }

    @Override
    public void updateBooking(Booking booking) {
        bookingRepositoryInterface.updateBooking(booking);
    }

   
    
    
}
