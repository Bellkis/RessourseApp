package org.groupe2.iactic5.model.timeslot.repositories;

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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.groupe2.iactic5.model.timeslot.entities.TimeSlot;

public class TimeSlotFileRepository implements TimeSlotRepositoryInterface {

    private static final String FILE_NAME = "timeslots.txt";
    private Map<Long, TimeSlot> timeSlotsMap;

    public TimeSlotFileRepository() {
        this.timeSlotsMap = loadTimeSlotsFromFile();
    }

    @Override
    public void saveTimeSlot(TimeSlot timeSlot) {
        timeSlotsMap.put(timeSlot.getId(), timeSlot);
        saveTimeSlotsToFile();
        System.out.println("Créneau horaire " + timeSlot.getId() + " créé : " + timeSlot.getStartTime() + ", " + timeSlot.getEndTime());
    }

    @Override
    public void deleteTimeSlot(long timeSlotId) {
        TimeSlot deletedTimeSlot = timeSlotsMap.remove(timeSlotId);
        if (deletedTimeSlot != null) {
            saveTimeSlotsToFile();
            System.out.println("Créneau horaire " + deletedTimeSlot.getId() + " supprimé : " + deletedTimeSlot.getId() + ", " + deletedTimeSlot.getStartTime() + ", "
                    + deletedTimeSlot.getEndTime());
        } else {
            System.err.println("Créneau horaire non trouvé avec l'ID : " + timeSlotId);
        }
    }

    @Override
    public Map<Long, TimeSlot> getAllTimeSlots() {
        return timeSlotsMap;
    }

    @Override
    public TimeSlot getTimeSlotByID(long timeSlotId) {
        return timeSlotsMap.get(timeSlotId);
    }

    private void saveTimeSlotsToFile() {
        URL resource = getClass().getClassLoader().getResource("");

        if (resource != null) {
            Path filePath;
            try {
                filePath = Paths.get(resource.toURI().resolve(FILE_NAME));

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                    for (TimeSlot timeSlot : timeSlotsMap.values()) {
                        writer.write(timeSlot.getId() + "," + timeSlot.getStartTime() + "," + timeSlot.getEndTime());
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (URISyntaxException e) {
                System.err.println("Erreur lors de la résolution de : " + FILE_NAME);
            }
        }
    }

    private Map<Long, TimeSlot> loadTimeSlotsFromFile() {
        Map<Long, TimeSlot> timeSlotMap = new HashMap<>();

        URL resource = getClass().getClassLoader().getResource(FILE_NAME);
        if (resource != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    long id = Long.parseLong(parts[0]);
                    LocalDateTime startTime = LocalDateTime.parse(parts[1]);
                    LocalDateTime endTime = LocalDateTime.parse(parts[2]);

                    TimeSlot timeSlot = new TimeSlot(id, startTime, endTime);
                    timeSlotMap.put(id, timeSlot);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Le fichier " + FILE_NAME
                    + " n'a pas été trouvé dans les ressources. Création du fichier avec un exemple de contenu...");
            createExampleTimeSlotFile();
            return loadTimeSlotsFromFile();
        }

        return timeSlotMap;
    }

    private void createExampleTimeSlotFile() {
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
