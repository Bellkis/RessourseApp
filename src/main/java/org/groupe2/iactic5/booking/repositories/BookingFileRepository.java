package org.groupe2.iactic5.booking.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.groupe2.iactic5.booking.entities.Booking;
import org.groupe2.iactic5.room.entities.Room;

public class BookingFileRepository implements BookingRepositoryInterface {

    private static final String FILE_NAME = "bookings.txt";
    private Map<Long, Booking> bookingMap;
    Logger logger = Logger.getLogger(getClass().getName());


    public BookingFileRepository() {
        this.bookingMap = loadBookingsFromFile();
    }


    @Override
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookingMap.values());
    }

    @Override
    public Booking getBookingById(long id) {
        return bookingMap.get(id);
    }

    @Override
    public void saveBooking(Booking booking) {
        bookingMap.put(booking.getId(), booking);
        saveBookingsToFile();
    }

    @Override
    public void deleteBooking(long id) {
        bookingMap.remove(id);
        saveBookingsToFile();
    }

    @Override
    public void updateBooking(Booking booking) {
        if (bookingMap.containsKey(booking.getId())) {
            bookingMap.put(booking.getId(), booking);
            saveBookingsToFile();
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
                    long roomId = Long.parseLong(parts[1]);

                    Booking booking = new Booking(id, getRoomById(roomId));
                    booking.setId(id);
                    booking.setRoom(null);
                  
                    roomMap.put(id, room);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Le fichier " + FILE_NAME + " n'a pas été trouvé dans les ressources. Création du fichier avec un exemple de contenu...");
            createExampleFile();
            return loadRoomsFromFile();
        }

        return roomMap;

    }

    private void createExampleFile() {
        try {
            // Utilise la classe ClassLoader pour obtenir le chemin du répertoire de
            // ressources
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource("");
            if (resource == null) {
                throw new IllegalStateException("Répertoire de ressources introuvable.");
            }

            // Crée le chemin complet du fichier dans le répertoire de ressources
            Path filePath = Paths.get(resource.toURI()).resolve(FILE_NAME);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                // Exemple de contenu du fichier
                writer.write("-1,Chambre -1,-1");
                writer.newLine();
                writer.write("-2,Chambre -2,-2");

                logger.info("Le fichier rooms.txt a été créé avec succès à l'emplacement : " + filePath);
            }

        } catch (IOException | URISyntaxException e) {
            logger.info("Erreur lors de la création du fichier rooms.txt");
        }
    }

    private void saveBookingsToFile() {
        URL ressource = getClass().getClassLoader().getResource("");

        if (ressource != null) {
            Path filePath;
            try {
                filePath = Paths.get(ressource.toURI().resolve(FILE_NAME));

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                    for (Booking booking : bookingMap.values()) {
                        writer.write(booking.getId() + "," +  booking.getRoom().getId());
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

}
