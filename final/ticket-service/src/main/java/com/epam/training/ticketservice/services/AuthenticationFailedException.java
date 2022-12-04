package com.epam.training.ticketservice.services;

public class AuthenticationFailedException extends RuntimeException{

    public AuthenticationFailedException() { super(); }

    public AuthenticationFailedException(String message) { super(message); }
}
