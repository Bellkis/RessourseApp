package org.group2.iatic.domain.timeslot.repositories;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.group2.iatic.domain.timeslot.entities.TimeSlot;

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
        // Get the user's home directory
        String userHome = System.getProperty("user.home");

        // Specify the file path within the user's home directory
        Path filePath = Paths.get(userHome, FILE_NAME);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            for (TimeSlot timeSlot : timeSlotsMap.values()) {
                String line = String.format("%d,%s,%s",
                        timeSlot.getId(),
                        timeSlot.getStartTime().toString(), // Convert LocalDateTime to string
                        timeSlot.getEndTime().toString());   // Convert LocalDateTime to string

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            // Handle file writing exception
            e.printStackTrace();
            System.err.println("Error writing to " + FILE_NAME + ": " + e.getMessage());
        }
    }


    private Map<Long, TimeSlot> loadTimeSlotsFromFile() {
        Map<Long, TimeSlot> timeSlotMap = new HashMap<>();

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
                    LocalDateTime startTime = LocalDateTime.parse(parts[1]); // Assuming date-time string is in ISO_LOCAL_DATE_TIME format
                    LocalDateTime endTime = LocalDateTime.parse(parts[2]); // Assuming date-time string is in ISO_LOCAL_DATE_TIME format

                    TimeSlot timeSlot = new TimeSlot(id, startTime, endTime);
                    timeSlotMap.put(id, timeSlot);
                }
            } catch (IOException | DateTimeParseException e) {
                // Handle file reading or parsing exception
                e.printStackTrace();
                System.err.println("Error reading from " + FILE_NAME + ": " + e.getMessage());
            }
        } else {
            System.err.println("Le fichier " + FILE_NAME
                    + " n'existe pas. Création du fichier avec un exemple de contenu...");
            createExampleTimeSlotFile();
            // Reload time slots from the newly created file
            return loadTimeSlotsFromFile();
        }

        return timeSlotMap;
    }


    private void createExampleTimeSlotFile() {
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
