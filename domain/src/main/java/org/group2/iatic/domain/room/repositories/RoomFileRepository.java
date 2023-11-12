package org.group2.iatic.domain.room.repositories;

import org.group2.iatic.domain.room.entities.Room;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class RoomFileRepository implements RoomRepositoryInterface {

    private static final String FILE_NAME = "rooms.txt";
    private Map<Long, Room> roomsMap;

    public RoomFileRepository() {
        this.roomsMap = loadRoomsFromFile();
    }

    @Override
    // Sauvegarder la salle dans la map et dans le fichier
    public void saveRoom(Room room) {
        roomsMap.put(room.getId(), room);
        saveRoomsToFile();
        System.out.println("Salle " + room.getId() + " créée : " + room.getName());
    }

    @Override
    // Supprimer la salle de la map et du fichier
    public void deleteRoom(long roomId) {
        Room deletedRoom = roomsMap.remove(roomId);
        if (deletedRoom != null) {
            saveRoomsToFile();
            System.out.println("Salle " + deletedRoom.getId() + " supprimée : " + deletedRoom.getName());
        } else {
            System.err.println("Salle non trouvée avec l'ID : " + roomId);
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
        // Get the user's home directory
        String userHome = System.getProperty("user.home");

        // Specify the file path within the user's home directory
        Path filePath = Paths.get(userHome, FILE_NAME);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            for (Room room : roomsMap.values()) {
                writer.write(room.getId() + "," + room.getName());
                writer.newLine();
            }

        } catch (IOException e) {
            // Handle file writing exception
            e.printStackTrace();
            System.err.println("Error writing to " + FILE_NAME + ": " + e.getMessage());
        }
    }

    private Map<Long, Room> loadRoomsFromFile() {
        Map<Long, Room> roomMap = new HashMap<>();

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
                    String name = parts[1];

                    Room room = new Room(id, name);
                    roomMap.put(id, room);
                }
            } catch (IOException e) {
                // Handle file reading exception
                System.err.println("Error reading from " + FILE_NAME + ": " + e.getMessage());
            }
        } else {
            System.out.println("Le fichier " + FILE_NAME
                    + " n'existe pas. Création du fichier avec un exemple de contenu...");
            createExampleRoomFile();
            // Reload rooms from the newly created file
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

            String userHome = System.getProperty("user.home");
            // Specify the file path within the user's home directory
            Path filePath = Paths.get(userHome, FILE_NAME);

            if (Files.exists(filePath)) {
                System.out.println("Le fichier rooms.txt existe déjà à l'emplacement : " + filePath);
            } else {
                Files.createFile(filePath);
                System.out.println("Le fichier rooms.txt a été créé avec succès à l'emplacement : " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier bookings.txt");
        }
    }

}
