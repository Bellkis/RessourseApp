package org.groupe2.iactic5.model.person.service;

import org.groupe2.iactic5.model.person.entities.Person;

import java.util.Map;

public interface PersonServicesInterface {

    /**
     * Crée une nouvelle Personne. la modifie si une Personne de même id existe
     *
     * @param person La réservation à créer.
     */
    void createPerson(Person person);

    /**
     * Supprime une Personne ne fait rien si la personne n'existe pas.
     *
     * @param personId La Personne à supprimer.
     */
    void deletePerson(long personId);

    /**
     * Recupère une Personne par son ID. retourne null si la personne n'existe pas
     * 
     * @param personId L'ID de la personne à récupérer
     * @return Person
     */
    Person getPersonById(long personId);

    /**
     * Récupere la map contenant toutes les personnes existantes
     * 
     * @return Map<Long, Person>
     */
    Map<Long, Person> getAllPersons();

    /**
     * affiche les personnes existantes
     */
    void displayPersons();

}
