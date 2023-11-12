package org.group2.iatic.domain.room.repositories;

import java.util.Map;

import org.group2.iatic.domain.room.entities.Room;

public interface RoomRepositoryInterface {

    /**
     * Sauvegarde la salle dans la map et dans le fichier, la modifie si une salle
     * de même id
     * 
     * @param room la salle à sauvegarder
     */
    void saveRoom(Room room);

    /**
     * Supprime la salle dans la map et dans le fichier
     * 
     * @param roomId Id de la salle à supprimer
     */
    void deleteRoom(long roomId);

    /**
     * Récupere la salle par son id, Retourne null si elle n'existe pas
     * 
     * @param roomId Id de la salle à récuperer
     * @return Room
     */
    Room getRoomById(long roomId);

    /**
     * Récupère la map contenant toutes les salles
     * 
     * @return Map<Long, Room>
     */
    Map<Long, Room> getAllRooms();

}
