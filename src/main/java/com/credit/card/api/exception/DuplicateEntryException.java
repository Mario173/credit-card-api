package com.credit.card.api.exception;

/**
 * Exception thrown when a duplicate entry is found
 */
public class DuplicateEntryException extends RuntimeException {

    /**
     * Constructor for DuplicateEntryException
     *
     * @param message exception message
     */
    public DuplicateEntryException(String message) {
        super(message);
    }

}
