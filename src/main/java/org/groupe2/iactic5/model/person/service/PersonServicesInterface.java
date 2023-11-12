package org.groupe2.iactic5.model.person.service;

import org.groupe2.iactic5.model.person.entities.Person;

public interface PersonServicesInterface {

    void createPerson(Person person);

    void deletePerson(long personId);

    Person getPersonById(long personId);

    void displayPersons();

}
