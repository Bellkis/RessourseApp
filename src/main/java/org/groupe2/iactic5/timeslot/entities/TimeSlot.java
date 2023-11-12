package org.groupe2.iactic5.timeslot.entities;

import java.time.LocalDateTime;

public class TimeSlot {

    private long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlot(long id, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Méthode pour vérifier si deux TimeSlot se chevauchent
    public boolean overlapsWith(TimeSlot other) {
        // Si l'une des périodes commence après que l'autre se termine, elles ne se
        // chevauchent pas
        return this.getEndTime().isBefore(other.getStartTime()) || other.getEndTime().isBefore(this.getStartTime());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}
