package org.groupe2.iactic5.model.person.repositories;

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

import org.groupe2.iactic5.model.person.entities.Person;

public class PersonFileRepository implements PersonRepositoryInterface {

    private static final String FILE_NAME = "persons.txt";
    private Map<Long, Person> personsMap;
    Logger logger = Logger.getLogger(getClass().getName());

    public PersonFileRepository() {
        this.personsMap = loadPersonsFromFile();
    }

    @Override
    // Sauvegarder la personne dans la list et dans le fichier
    public void savePerson(Person person) {
        personsMap.put(person.getId(), person);
        savePersonsToFile();
        logger.info("Personne créée : " + person.getName());
    }

    @Override
    // Supprimer la personne de la liste et du fichier
    public void deletePerson(long personId) {
        Person deletedPerson = personsMap.remove(personId);
        if (deletedPerson != null) {
            savePersonsToFile();
            logger.info("Personne supprimée : " + deletedPerson.getName());
        } else {
            logger.info("Personne non trouvée avec l'ID : " + personId);
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
        URL resource = getClass().getClassLoader().getResource("");

        if (resource != null) {
            Path filePath;
            try {
                filePath = Paths.get(resource.toURI().resolve(FILE_NAME));

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                    for (Person person : personsMap.values()) {
                        writer.write(person.getId() + "," + person.getName());
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (URISyntaxException e) {
                logger.info("Erreur lors de la résolution de : " + FILE_NAME);
            }
        }
    }

    private Map<Long, Person> loadPersonsFromFile() {

        Map<Long, Person> personMap = new HashMap<>();

        URL resource = getClass().getClassLoader().getResource(FILE_NAME);
        if (resource != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    Long id = Long.parseLong(parts[0]);
                    String name = parts[1];
                    Person person = new Person(id, name);
                    personMap.put(id, person);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Le fichier " + FILE_NAME
                    + " n'a pas été trouvé dans les ressources. Création du fichier avec un exemple de contenu...");
            createExamplePersonFile();
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

            // Crée le chemin complet du fichier dans le répertoire de ressources
            Path filePath = Paths.get(resource.toURI()).resolve(FILE_NAME);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                // Exemple de contenu du fichier
                writer.write("-1,Eliel");
                writer.newLine();
                writer.write("-2,Bellkis");

                logger.info("Le fichier persons.txt a été créé avec succès à l'emplacement : " + filePath);
            }

        } catch (IOException | URISyntaxException e) {
            logger.info("Erreur lors de la création du fichier persons.txt");
        }
    }

}
