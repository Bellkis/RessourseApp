package org.groupe2.iactic5.model.person.repositories;

import java.util.Map;

import org.groupe2.iactic5.model.person.entities.Person;

public interface PersonRepositoryInterface {

    void savePerson(Person person);

    void deletePerson(long personId);

    Person getPersonById(long personId);

    Map<Long, Person> getAllPersons();

}
