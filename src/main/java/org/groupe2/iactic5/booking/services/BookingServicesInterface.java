package org.groupe2.iactic5.booking.services;

import org.groupe2.iactic5.booking.entities.Booking;
import org.groupe2.iactic5.person.entities.Person;
import org.groupe2.iactic5.room.entities.Room;
import org.groupe2.iactic5.timeslot.entities.TimeSlot;

import java.util.List;


public interface BookingServicesInterface {

   void createBooking(Booking booking);

   void createManyBookings(long id, Person person, Room room, List<TimeSlot> timeSlots);

   void deleteBooking(long id);

   void updateBooking(long bookingId, TimeSlot timeSlot);

   Booking getBookingById(long id);

   void displayBookings();



}
