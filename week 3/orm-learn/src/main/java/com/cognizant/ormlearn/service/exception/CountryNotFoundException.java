package com.cognizant.ormlearn.service.exception;

/**
 * Doc 1 - Hands on 6: Find a country based on country code.
 * Thrown when a country code does not exist in the database.
 */
public class CountryNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public CountryNotFoundException(String message) {
        super(message);
    }
}
