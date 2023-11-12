package org.groupe2.iactic5.model.views_actions;

import org.group2.iatic.domain.person.entities.Person;
import org.group2.iatic.domain.person.repositories.PersonRepositoryInterface;
import org.group2.iatic.ihm.actions.GuestsActions;
import org.group2.iatic.ihm.models.PersonDataModel;
import org.group2.iatic.ihm.utils.Generators;

import java.util.List;
import java.util.stream.Collectors;

public class GuestsActionsImpl implements GuestsActions {

    PersonRepositoryInterface personRepository;

    public GuestsActionsImpl(PersonRepositoryInterface personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void addGuest(String s) {
        personRepository.savePerson(new Person((long) Generators.generateRandomIntBasedOnTime(), s));
        guestsUpdates.set(!guestsUpdates.get());
    }

    @Override
    public void deleteGuest(PersonDataModel personDataModel) {
        personRepository.deletePerson(personDataModel.getId());
        guestsUpdates.set(!guestsUpdates.get());
    }

    @Override
    public List<PersonDataModel> getGuests() {
        return personRepository.getAllPersons().values().stream().map(GuestsActionsImpl::fromPerson).collect(Collectors.toList());
    }

    public static PersonDataModel fromPerson(Person person) {
        return new PersonDataModel(person.getId().intValue(), person.getName());
    }
}
