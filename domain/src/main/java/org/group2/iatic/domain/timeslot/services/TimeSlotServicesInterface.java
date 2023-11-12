package org.group2.iatic.domain.timeslot.services;

import org.group2.iatic.domain.timeslot.entities.TimeSlot;

import java.util.Map;

public interface TimeSlotServicesInterface {

    /**
     * Sauvegarde le créneau dans la map et dans le fichier, le modifie si un
     * créneau
     * de même id
     * 
     * @param timeSlot le créneau à sauvegarder
     */
    void createTimeSlot(TimeSlot timeSlot);

    /**
     * Supprime le créneau dans la map et dans le fichier
     * 
     * @param timeSlotId Id du créneau à supprimer
     */
    void deleteTimeSlot(long timeSlotid);

    /**
     * Récupere le créneau par son id, Retourne null si il n'existe pas
     * 
     * @param timeSlotId Id du créneau à récuperer
     * @return TimeSlot
     */
    TimeSlot getTimeSlotById(long timeSlotId);

    /**
     * Récupère la map contenant tous les créneaux
     * 
     * @return Map
     */
    Map<Long, TimeSlot> getAllTimeSlots();

    /**
     * Affiche tous les créneaux existantes
     */
    void displayTimeSlots();
}
