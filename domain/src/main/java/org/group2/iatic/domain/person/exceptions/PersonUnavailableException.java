package org.group2.iatic.domain.person.exceptions;

public class PersonUnavailableException extends RuntimeException {
    public PersonUnavailableException(String message) {
        super(message);
    }
}
