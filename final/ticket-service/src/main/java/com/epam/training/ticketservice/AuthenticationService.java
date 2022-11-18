package com.epam.training.ticketservice;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private final UserRepository userRepository;
    @Getter
    private User loggedInUser;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticate(String username, String password) {
        //TODO authentication exep
        var user = userRepository.findById(username).orElseThrow();
        if (password.equals(user.getPassword())) {
            loggedInUser = user;
        }
        else {
            throw new RuntimeException();
        }
    }

    public void logOut() {
        loggedInUser = null;
    }
}
