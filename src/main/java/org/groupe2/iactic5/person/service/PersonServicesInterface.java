package org.groupe2.iactic5.person.service;

import org.groupe2.iactic5.person.entities.Person;

public interface PersonServicesInterface {

    void createPerson(Person person);

    void deletePerson(long personId);

    Person getPersonById(long personId);

    void displayPersons();

}
