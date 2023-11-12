package org.groupe2.iactic5.model.booking.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.groupe2.iactic5.model.booking.entities.Booking;
import org.groupe2.iactic5.model.person.service.PersonServicesInterface;
import org.groupe2.iactic5.model.room.services.RoomServicesInterface;
import org.groupe2.iactic5.model.timeslot.services.TimeSlotServicesInterface;

public class BookingFileRepository implements BookingRepositoryInterface {

    private static final String FILE_NAME = "bookings.txt";
    private PersonServicesInterface personServicesInterface;
    private RoomServicesInterface roomServicesInterface;
    private TimeSlotServicesInterface timeSlotServicesInterface;
    private Map<Long, Booking> bookingsMap;
    Logger logger = Logger.getLogger(getClass().getName());

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
        logger.info("Réservation créée : " + booking.getId());
    }

    @Override
    // Supprimer la réservation de la map et du fichier
    public void deleteBooking(long bookingId) {
        Booking deletedBooking = bookingsMap.remove(bookingId);
        if (deletedBooking != null) {
            saveBookingsToFile();
            logger.info("Réservation supprimée : " + deletedBooking.getId());
        } else {
            logger.info("Réservation non trouvée avec l'ID : " + bookingId);
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
        URL ressource = getClass().getClassLoader().getResource("");

        if (ressource != null) {
            Path filePath;
            try {
                filePath = Paths.get(ressource.toURI().resolve(FILE_NAME));

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                    for (Booking booking : bookingsMap.values()) {
                        writer.write(booking.getId() + "," + booking.getPerson().getId() + "," + booking.getRoom().getId()
                                + "," + booking.getTimeSlot().getId());
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (URISyntaxException e) {
                logger.info("Error lors de la resolution de : " + FILE_NAME);
            }

        }

    }

    private Map<Long, Booking> loadBookingsFromFile() {

        Map<Long, Booking> bookingMap = new HashMap<>();
        URL resource = getClass().getClassLoader().getResource(FILE_NAME);

        if (resource != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    long id = Long.parseLong(parts[0]);
                    long personId = Long.parseLong(parts[1]);
                    long roomId = Long.parseLong(parts[2]);
                    long timeSlotId = Long.parseLong(parts[3]);

                    Booking booking = new Booking(id, this.personServicesInterface.getPersonById(personId),
                            this.roomServicesInterface.getRoomById(roomId),
                            this.timeSlotServicesInterface.getTimeSlotById(timeSlotId));
                    bookingMap.put(id, booking);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Le fichier " + FILE_NAME
                    + " n'a pas été trouvé dans les ressources. Création du fichier avec un exemple de contenu...");
            createExampleBookingFile();
            return loadBookingsFromFile();
        }

        return bookingMap;
    }

    private void createExampleBookingFile() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource("");
            if (resource == null) {
                throw new IllegalStateException("Répertoire de ressources introuvable.");
            }

            // Crée le chemin complet du fichier dans le répertoire de ressources
            Path filePath = Paths.get(resource.toURI()).resolve(FILE_NAME);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                // Exemple de contenu du fichier pour les réservations
                writer.write("-1,-1,-1,-1");
                writer.newLine();
                writer.write("-2,-2,-2,-2");
                writer.newLine();

                logger.info("Le fichier bookings.txt a été créé avec succès à l'emplacement : " + filePath);
            }

        } catch (IOException | URISyntaxException e) {
            logger.info("Erreur lors de la création du fichier bookings.txt");
        }
    }

}
