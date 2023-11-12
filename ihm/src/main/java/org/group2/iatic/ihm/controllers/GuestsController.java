package org.group2.iatic.ihm.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.group2.iatic.ihm.actions.GuestsActions;
import org.group2.iatic.ihm.models.PersonDataModel;

import java.util.List;

public class GuestsController {

    GuestsActions guestsActions;

     public List<PersonDataModel> personDataModels;

    public BooleanProperty guestUpdates = new SimpleBooleanProperty(true);

    public GuestsController(GuestsActions guestsActions) {
        this.guestsActions = guestsActions;
        this.personDataModels = guestsActions.getGuests();
        guestsActions.guestsUpdates.addListener((observableValue, aBoolean, t1) -> {
            this.personDataModels = guestsActions.getGuests();
            guestUpdates.set(observableValue.getValue().booleanValue());
        });
    }

    public void onDeleteGuest(PersonDataModel personDataModel) {
         guestsActions.deleteGuest(personDataModel);
     }

    public void onAddGuest(String name)  {
        guestsActions.addGuest(name);
    }
}
