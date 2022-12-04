package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.domain.Role;
import com.epam.training.ticketservice.services.AuthenticationFailedException;
import com.epam.training.ticketservice.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class ApplicationCommands extends SecuredCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationCommands.class);

    public ApplicationCommands(AuthenticationService authenticationService) {
        super(authenticationService);
    }

    @ShellMethod(key = "sign in privileged")
    public void signIn(String username, String password) {
        try {
            authenticationService.authenticate(username, password);
            LOGGER.info("Signed in!");
        } catch (AuthenticationFailedException e) {
            System.out.println("Login failed due to incorrect credentials");
        }
    }

    @ShellMethod(key = "sign out")
    @ShellMethodAvailability(value = "isAdmin")
    public void signOut() {
        authenticationService.logOut();
        LOGGER.info("Signed out!");
    }

    @ShellMethod(key = "describe account")
    public void describeAccount() {
        var user = authenticationService.getLoggedInUser();
        if (user != null && user.getRole().equals(Role.ADMIN)) {
            System.out.println("Signed in with privileged account " + "'" + user.getUsername() + "'");
        } else {
            System.out.println("You are not signed in");
        }
    }
}
