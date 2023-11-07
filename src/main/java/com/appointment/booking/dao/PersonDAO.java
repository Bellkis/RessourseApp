package com.appointment.booking.dao;

import com.appointment.booking.models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonDAO {

    static final String tableName = "Persons";
    private static final String idColumn = "id";
    private static final String nameColumn = "name";

    final static Map<String, String> columns = new HashMap<String, String>() {{
        put(idColumn, "INTEGER PRIMARY KEY AUTOINCREMENT");
        put(nameColumn, "TEXT");
    }};

    private static final ObservableList<Person> persons;

    static {
        persons = FXCollections.observableArrayList();
        updatePersonsFromDB();
    }

    public static ObservableList<Person> getPersons() {
        return persons;
    }

    private static void updatePersonsFromDB() {

        String query = "SELECT * FROM " + tableName;

        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            persons.clear();
            while (rs.next()) {
                persons.add(new Person(
                        rs.getInt(idColumn),
                        rs.getString(nameColumn)
                ));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            persons.clear();
        }
    }

    public static void update(Person newPerson) {
        //udpate database
        int rows = CRUDHelper.update(
                tableName,
                new String[]{nameColumn},
                new Object[]{newPerson.getName()},
                new int[]{Types.VARCHAR},
                idColumn,
                Types.INTEGER,
                newPerson.getId()
        );

        if (rows == 0)
            throw new IllegalStateException("Person to be updated with id " + newPerson.getId() + " didn't exist in database");

        //update cache
        Optional<Person> optionalPerson = getPerson(newPerson.getId());
        optionalPerson.ifPresentOrElse((oldPerson) -> {
            persons.remove(oldPerson);
            persons.add(newPerson);
        }, () -> {
            throw new IllegalStateException("Person to be updated with id " + newPerson.getId() + " didn't exist in database");
        });
        updatePersonsFromDB();
    }

    public static void insertPerson(String name) {
        //update database
        int id = (int) CRUDHelper.create(
                tableName,
                new String[]{nameColumn},
                new Object[]{name},
                new int[]{Types.VARCHAR});

        //update cache
        persons.add(new Person(
                id,
                name
        ));
        updatePersonsFromDB();
    }

    public static void delete(int id) {
        //update database
        CRUDHelper.delete(tableName, id);

        //update cache
        Optional<Person> person = getPerson(id);
        person.ifPresent(persons::remove);
        updatePersonsFromDB();
    }

    public static Optional<Person> getPerson(int id) {
        for (Person person : persons) {
            if (person.getId() == id) return Optional.of(person);
        }
        return Optional.empty();
    }
}