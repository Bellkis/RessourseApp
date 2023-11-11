package org.groupe2.iactic5.room.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.groupe2.iactic5.room.entities.Room;

public class RoomFileRepository implements RoomRepositoryInterface {

    private static final String FILE_NAME = "rooms.txt";
    private Map<Long, Room> rooms;
    Logger logger = Logger.getLogger(getClass().getName());

    public RoomFileRepository() {
        this.rooms = loadRoomsFromFile();
    }

    @Override
    public void saveRoom(Room room) {
        // Sauvegarder la salle dans la map et dans le fichier
        rooms.put(room.getId(), room);
        saveRoomsToFile();
        logger.info("Salle créée : " + room.getName());
    }

    @Override
    public void deleteRoom(long roomId) {
        // Supprimer la salle de la map et du fichier
        Room deletedRoom = rooms.remove(roomId);
        if (deletedRoom != null) {
            saveRoomsToFile();
            logger.info("Salle supprimée : " + deletedRoom.getName());
        } else {
            logger.info("Salle non trouvée avec l'ID : " + roomId);
        }
    }

    // Dans la méthode loadRoomsFromFile() de FileRepository.java
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
                    int capacity = Integer.parseInt(parts[2]);
                    Room room = new Room();
                    room.setId(id);
                    room.setName(name);
                    room.setCapacity(capacity);
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

    private void saveRoomsToFile() {
        URL ressource = getClass().getClassLoader().getResource("");

        if (ressource != null) {
            Path filePath;
            try {
                filePath = Paths.get(ressource.toURI().resolve(FILE_NAME));

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                    for (Room room : rooms.values()) {
                        writer.write(room.getId() + "," + room.getName() + "," + room.getCapacity());
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

    @Override
    public Room getRoomById(long roomId) {
        return rooms.get(roomId);
        
    }

}
