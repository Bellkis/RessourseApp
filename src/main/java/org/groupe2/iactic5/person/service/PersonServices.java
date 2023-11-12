package org.groupe2.iactic5.person.service;

import java.util.Map;

import org.groupe2.iactic5.person.entities.Person;
import org.groupe2.iactic5.person.repositories.PersonRepositoryInterface;

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
        return personRepositoryInterface.getPersonById(personId);
    }

    public void setPersonRepositoryInterface(PersonRepositoryInterface personRepositoryInterface) {
        this.personRepositoryInterface = personRepositoryInterface;
    }

    @Override
    public void displayPersons() {
        Map<Long, Person> personsMap = personRepositoryInterface.getAllPersons();
        System.out.println("Persons Map:");
        for (Map.Entry<Long, Person> entry : personsMap.entrySet()) {
            Person person = entry.getValue();
            System.out.println("Person ID: " + person.getId() + ", Name: " + person.getName());
        }
        System.out.println();
    }

}
