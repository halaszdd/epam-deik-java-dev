package com.epam.training.ticketservice.services;

public class ScreeningBreaktimeOverlapException extends RuntimeException {

    public ScreeningBreaktimeOverlapException() {
        super();
    }

    public ScreeningBreaktimeOverlapException(String message) {
        super(message);
    }
}
