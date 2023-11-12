package org.groupe2.iactic5.model.person.repositories;

import java.util.Map;

import org.groupe2.iactic5.model.person.entities.Person;

public interface PersonRepositoryInterface {

    /**
     * Sauvegarde la personne dans la map et dans le fichier, la modifie si une
     * persone de même id existe
     * 
     * @person la personne à sauvegarder
     */
    void savePerson(Person person);

    /**
     * Supprime la personne de la map et du fichier
     * 
     * @personId Id de la personne à supprimer
     */
    void deletePerson(long personId);

    /**
     * Récupere la personne par son id, Retourne null si elle n'existe pas
     * 
     * @return Personne
     */
    Person getPersonById(long personId);

    /**
     * Recupère la map contenant toutes les personnes
     * 
     * @return Map<Long, Person>
     */
    Map<Long, Person> getAllPersons();

}
