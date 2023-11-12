package org.group2.iatic.domain.person.repositories;

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


import org.group2.iatic.domain.person.entities.Person;

public class PersonFileRepository implements PersonRepositoryInterface {

    private static final String FILE_NAME = "persons.txt";
    private Map<Long, Person> personsMap;

    public PersonFileRepository() {
        this.personsMap = loadPersonsFromFile();
    }

    @Override
    // Sauvegarder la personne dans la list et dans le fichier
    public void savePerson(Person person) {
        personsMap.put(person.getId(), person);
        savePersonsToFile();
        System.out.println("Personne créée : " + person.getName());
    }

    @Override
    // Supprimer la personne de la liste et du fichier
    public void deletePerson(long personId) {
        Person deletedPerson = personsMap.remove(personId);
        if (deletedPerson != null) {
            savePersonsToFile();
            System.out.println("Personne " + deletedPerson.getId() + " supprimée : " + deletedPerson.getName());
        } else {
            System.out.println("Personne non trouvée avec l'ID : " + personId);
        }
    }

    @Override
    public Map<Long, Person> getAllPersons() {
        return personsMap;
    }

    @Override
    public Person getPersonById(long personId) {
        return personsMap.get(personId);
    }

    private void savePersonsToFile() {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");

        // Specify the file path within the user's home directory
        Path filePath = Paths.get(userHome, FILE_NAME);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Person person : personsMap.values()) {
                writer.write(person.getId() + "," + person.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle file writing exception
            e.printStackTrace();
            System.err.println("Error writing to persons.txt: " + e.getMessage());
        }
    }

    private Map<Long, Person> loadPersonsFromFile() {
        Map<Long, Person> personMap = new HashMap<>();

        Path filePath = Paths.get(System.getProperty("user.home"), FILE_NAME);

        if (Files.exists(filePath)) {
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    Long id = Long.parseLong(parts[0]);
                    String name = parts[1];
                    Person person = new Person(id, name);
                    personMap.put(id, person);
                }
            } catch (IOException e) {
                // Handle file reading exception
                System.err.println("Error reading from persons.txt: " + e.getMessage());
            }
        } else {
            // If the file doesn't exist, create an example file
            System.out.println("Le fichier persons.txt n'existe pas. Création du fichier avec un exemple de contenu...");
            createExamplePersonFile();
            // Reload persons from the newly created file
            return loadPersonsFromFile();
        }

        return personMap;
    }


    private void createExamplePersonFile() {
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
                System.out.println("Le fichier persons.txt existe déjà à l'emplacement : " + filePath);
            } else {
                Files.createFile(filePath);
                System.out.println("Le fichier persons.txt a été créé avec succès à l'emplacement : " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier persons.txt");
        }
    }

}
