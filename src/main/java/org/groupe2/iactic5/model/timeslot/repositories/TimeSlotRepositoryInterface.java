package org.groupe2.iactic5.model.timeslot.repositories;

import java.util.Map;

import org.groupe2.iactic5.model.timeslot.entities.TimeSlot;

public interface TimeSlotRepositoryInterface {

    /**
     * Sauvegarde le créneau dans la map et dans le fichier, le modifie si un
     * créneau
     * de même id
     * 
     * @param timeSlot le créneau à sauvegarder
     */
    void saveTimeSlot(TimeSlot timeSlot);

    /**
     * Supprime le créneau dans la map et dans le fichier
     * 
     * @param timeSlotId Id du créneau à supprimer
     */
    void deleteTimeSlot(long timeSlotId);

    /**
     * Récupere le créneau par son id, Retourne null si il n'existe pas
     * 
     * @param timeSlotId Id du créneau à récuperer
     * @return TimeSlot
     */
    TimeSlot getTimeSlotByID(long timeSlotId);

    /**
     * Récupère la map contenant tous les créneaux
     * 
     * @return Map
     */
    Map<Long, TimeSlot> getAllTimeSlots();

}
