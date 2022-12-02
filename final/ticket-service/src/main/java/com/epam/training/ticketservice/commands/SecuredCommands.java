package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.domain.Role;
import com.epam.training.ticketservice.services.AuthenticationService;
import org.springframework.shell.Availability;

public abstract class SecuredCommands {

    protected final AuthenticationService authenticationService;

    protected SecuredCommands(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    protected Availability isAdmin() {
        var user = authenticationService.getLoggedInUser();
        return user != null && user.getRole().equals(Role.ADMIN)
                ? Availability.available() : Availability.unavailable("Command only allowed for admins!");
    }

}
