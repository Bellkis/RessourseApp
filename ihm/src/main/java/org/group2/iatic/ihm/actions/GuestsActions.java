package org.group2.iatic.ihm.actions;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.group2.iatic.ihm.models.PersonDataModel;

import java.util.List;

public interface GuestsActions {
    void addGuest(String name) ;

    void deleteGuest(PersonDataModel personDataModel);

    List<PersonDataModel> getGuests();

    BooleanProperty guestsUpdates = new SimpleBooleanProperty(true);
}
