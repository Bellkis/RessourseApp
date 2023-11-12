package org.groupe2.iactic5.model.room.services;

import org.groupe2.iactic5.model.room.entities.Room;
import java.util.Map;

public interface RoomServicesInterface {

    /**
     * Sauvegarde la salle dans la map et dans le fichier, la modifie si une salle
     * de même id
     * 
     * @param room la salle à sauvegarder
     */
    void createRoom(Room room);

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

    /**
     * affiche toutes les salle existantes
     */
    void displayRooms();

}