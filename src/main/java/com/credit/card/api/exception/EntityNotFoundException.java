package com.credit.card.api.exception;

/**
 * Exception thrown when an entity is not found
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructor for EntityNotFoundException
     *
     * @param message exception message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

}
