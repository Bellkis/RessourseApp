package org.groupe2.iactic5.model.person.service;

import java.util.Map;

import org.groupe2.iactic5.model.person.entities.Person;
import org.groupe2.iactic5.model.person.exceptions.PersonNotFoundException;
import org.groupe2.iactic5.model.person.repositories.PersonRepositoryInterface;

public class PersonServices implements PersonServicesInterface {

    private PersonRepositoryInterface personRepositoryInterface;

    public PersonServices(PersonRepositoryInterface personRepositoryInterface) {
        this.personRepositoryInterface = personRepositoryInterface;
    }

    @Override
    public void createPerson(Person person) {
        personRepositoryInterface.savePerson(person);
    }

    @Override
    public void deletePerson(long personId) {
        personRepositoryInterface.deletePerson(personId);
    }

    @Override
    public Person getPersonById(long personId) {

        Person person = personRepositoryInterface.getPersonById(personId);
        if (person == null) {
            throw new PersonNotFoundException("Aucune personne trouvée avec l'ID : " + personId);
        }

        return person;
    }

    @Override
    public void displayPersons() {
        Map<Long, Person> personsMap = personRepositoryInterface.getAllPersons();
        System.out.println("\nPersons Map:");
        for (Map.Entry<Long, Person> entry : personsMap.entrySet()) {
            Person person = entry.getValue();
            System.out.println("Person ID: " + person.getId() + ", Name: " + person.getName());
        }
        System.out.println();
    }

    @Override
    public Map<Long, Person> getAllPersons() {
        return personRepositoryInterface.getAllPersons();
    }

    public void setPersonRepositoryInterface(PersonRepositoryInterface personRepositoryInterface) {
        this.personRepositoryInterface = personRepositoryInterface;
    }

}
