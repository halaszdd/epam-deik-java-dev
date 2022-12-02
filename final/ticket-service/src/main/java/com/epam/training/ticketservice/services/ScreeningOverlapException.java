package com.epam.training.ticketservice.services;

public class ScreeningOverlapException extends RuntimeException {

    public ScreeningOverlapException() {
        super();
    }

    public ScreeningOverlapException(String message) {
        super(message);
    }
}
