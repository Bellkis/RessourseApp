package org.group2.iatic.domain.booking.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.group2.iatic.domain.booking.entities.Booking;
import org.group2.iatic.domain.person.entities.Person;
import org.group2.iatic.domain.person.exceptions.PersonNotFoundException;
import org.group2.iatic.domain.person.service.PersonServicesInterface;
import org.group2.iatic.domain.room.entities.Room;
import org.group2.iatic.domain.room.exceptions.RoomNotFoundException;
import org.group2.iatic.domain.room.services.RoomServicesInterface;
import org.group2.iatic.domain.timeslot.entities.TimeSlot;
import org.group2.iatic.domain.timeslot.exceptions.TimeSlotNotFoundException;
import org.group2.iatic.domain.timeslot.services.TimeSlotServicesInterface;

public class BookingFileRepository implements BookingRepositoryInterface {

    private static final String FILE_NAME = "bookings.txt";
    private PersonServicesInterface personServicesInterface;
    private RoomServicesInterface roomServicesInterface;
    private TimeSlotServicesInterface timeSlotServicesInterface;
    private Map<Long, Booking> bookingsMap;

    public BookingFileRepository(PersonServicesInterface personServicesInterface,
            RoomServicesInterface roomServicesInterface, TimeSlotServicesInterface timeSlotServicesInterface) {
        this.personServicesInterface = personServicesInterface;
        this.roomServicesInterface = roomServicesInterface;
        this.timeSlotServicesInterface = timeSlotServicesInterface;
        this.bookingsMap = loadBookingsFromFile();
    }

    @Override
    // Sauvegarder la reservation dans la map et dans le fichier
    public void saveBooking(Booking booking) {
        bookingsMap.put(booking.getId(), booking);
        saveBookingsToFile();
        System.out.println("Réservation créée : " + booking.getId());
    }

    @Override
    // Supprimer la réservation de la map et du fichier
    public void deleteBooking(long bookingId) {
        Booking deletedBooking = bookingsMap.remove(bookingId);
        if (deletedBooking != null) {
            saveBookingsToFile();
            System.out.println("Réservation supprimée : " + deletedBooking.getId());
        } else {
            System.out.println("Réservation non trouvée avec l'ID : " + bookingId);
        }
    }

    @Override
    public void updateBooking(Booking booking) {
        if (bookingsMap.containsKey(booking.getId())) {
            bookingsMap.put(booking.getId(), booking);
            saveBookingsToFile();
            timeSlotServicesInterface.createTimeSlot(booking.getTimeSlot());
        }
    }

    @Override
    public Map<Long, Booking> getAllBookings() {
        return bookingsMap;
    }

    @Override
    public Booking getBookingById(long id) {
        return bookingsMap.get(id);
    }

    private void saveBookingsToFile() {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");

        // Specify the file path within the user's home directory
        Path filePath = Paths.get(userHome, FILE_NAME);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            for (Booking booking : bookingsMap.values()) {
                writer.write(String.format("%d,%d,%d,%d",
                        booking.getId(),
                        booking.getPerson().getId(),
                        booking.getRoom().getId(),
                        booking.getTimeSlot().getId()));
                writer.newLine();
            }

        } catch (IOException e) {
            // Handle file writing exception
            e.printStackTrace();
            System.err.println("Error writing to " + FILE_NAME + ": " + e.getMessage());
        }
    }

    private Map<Long, Booking> loadBookingsFromFile() {
        Map<Long, Booking> bookingMap = new HashMap<>();

        // Get the user's home directory
        String userHome = System.getProperty("user.home");

        // Specify the file path within the user's home directory
        Path filePath = Paths.get(userHome, FILE_NAME);

        if (Files.exists(filePath)) {
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    long id = Long.parseLong(parts[0]);
                    long personId = Long.parseLong(parts[1]);
                    long roomId = Long.parseLong(parts[2]);
                    long timeSlotId = Long.parseLong(parts[3]);

                    try {
                        Person person = personServicesInterface.getPersonById(personId);
                        Room room = roomServicesInterface.getRoomById(roomId);
                        TimeSlot timeSlot = timeSlotServicesInterface.getTimeSlotById(timeSlotId);

                        Booking booking = new Booking(id, person, room, timeSlot);
                        bookingMap.put(id, booking);
                    } catch (PersonNotFoundException | RoomNotFoundException | TimeSlotNotFoundException e) {
                        System.err.println("Error creating Booking: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                // Handle file reading exception
                System.err.println("Error reading from " + FILE_NAME + ": " + e.getMessage());
            }
        } else {
            System.err.println("Le fichier " + FILE_NAME
                    + " n'existe pas. Création du fichier avec un exemple de contenu...");
            createExampleBookingFile();
        }

        return bookingMap;
    }


    private void createExampleBookingFile() {
        try {
            // Get the user's home directory
            String userHome = System.getProperty("user.home");

            // Specify the file path within the user's home directory
            Path filePath = Paths.get(userHome, FILE_NAME);

            // Create the file without writing content
            Files.createFile(filePath);

            System.out.println("Le fichier " + FILE_NAME + " a été créé avec succès à l'emplacement : " + filePath);

        } catch (IOException e) {
            // Handle file creation exception
            e.printStackTrace();
            System.err.println("Erreur lors de la création du fichier " + FILE_NAME + ": " + e.getMessage());
        }
    }

}
