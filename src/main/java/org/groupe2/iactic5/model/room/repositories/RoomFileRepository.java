package org.groupe2.iactic5.model.room.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.groupe2.iactic5.model.room.entities.Room;

public class RoomFileRepository implements RoomRepositoryInterface {

    private static final String FILE_NAME = "rooms.txt";
    private Map<Long, Room> roomsMap;

    Logger logger = Logger.getLogger(getClass().getName());

    public RoomFileRepository() {
        this.roomsMap = loadRoomsFromFile();
    }

    @Override
    // Sauvegarder la salle dans la map et dans le fichier
    public void saveRoom(Room room) {
        roomsMap.put(room.getId(), room);
        saveRoomsToFile();
        logger.info("Salle créée : " + room.getName());
    }

    @Override
    // Supprimer la salle de la map et du fichier
    public void deleteRoom(long roomId) {
        Room deletedRoom = roomsMap.remove(roomId);
        if (deletedRoom != null) {
            saveRoomsToFile();
            logger.info("Salle supprimée : " + deletedRoom.getName());
        } else {
            logger.info("Salle non trouvée avec l'ID : " + roomId);
        }
    }

    @Override
    public Map<Long, Room> getAllRooms() {
        return roomsMap;
    }

    @Override
    public Room getRoomById(long roomId) {
        return roomsMap.get(roomId);

    }

    private void saveRoomsToFile() {
        URL ressource = getClass().getClassLoader().getResource("");

        if (ressource != null) {
            Path filePath;
            try {
                filePath = Paths.get(ressource.toURI().resolve(FILE_NAME));

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                    for (Room room : roomsMap.values()) {
                        writer.write(room.getId() + "," + room.getName());
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

    private Map<Long, Room> loadRoomsFromFile() {

        Map<Long, Room> roomMap = new HashMap<>();

        URL resource = getClass().getClassLoader().getResource(FILE_NAME);
        if (resource != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    long id = Long.parseLong(parts[0]);
                    String name = parts[1];

                    Room room = new Room(id, name);
                    roomMap.put(id, room);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Le fichier " + FILE_NAME
                    + " n'a pas été trouvé dans les ressources. Création du fichier avec un exemple de contenu...");
            createExampleRoomFile();
            return loadRoomsFromFile();
        }

        return roomMap;

    }

    private void createExampleRoomFile() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource("");
            if (resource == null) {
                throw new IllegalStateException("Répertoire de ressources introuvable.");
            }

            // Crée le chemin complet du fichier dans le répertoire de ressources
            Path filePath = Paths.get(resource.toURI()).resolve(FILE_NAME);

            // Crée le fichier sans écrire de contenu
            Files.createFile(filePath);

            System.out.println("Le fichier bookings.txt a été créé avec succès à l'emplacement : " + filePath);

        } catch (IOException | URISyntaxException e) {
            System.err.println("Erreur lors de la création du fichier bookings.txt");
        }
    }

}
