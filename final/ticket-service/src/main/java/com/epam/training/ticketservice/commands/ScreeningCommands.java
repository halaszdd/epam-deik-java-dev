package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.domain.RegisterScreeningModel;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.services.AuthenticationService;
import com.epam.training.ticketservice.services.ScreeningBreaktimeOverlapException;
import com.epam.training.ticketservice.services.ScreeningOverlapException;
import com.epam.training.ticketservice.services.ScreeningService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ShellComponent
public class ScreeningCommands extends SecuredCommands {

    private final ScreeningService screeningService;

    public ScreeningCommands(AuthenticationService authenticationService, ScreeningService screeningService) {
        super(authenticationService);
        this.screeningService = screeningService;
    }

    @ShellMethod(key = "create screening")
    @ShellMethodAvailability(value = "isAdmin")
    public void createScreening(String title, String name, String time) {

        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        var parsedTime = LocalDateTime.parse(time, formatter);

        var screening = RegisterScreeningModel.builder()
                .title(title)
                .name(name)
                .time(parsedTime)
                .build();
        try {
            screeningService.createScreening(screening);
        }
        catch (ScreeningOverlapException overlapException) {
            System.out.println("There is an overlapping screening");
        }
        catch (ScreeningBreaktimeOverlapException breaktimeOverlapException) {
            System.out.println("This would start in the break period after another screening in this room");
        }
    }

    @ShellMethod(key = "delete screening")
    @ShellMethodAvailability(value = "isAdmin")
    public void deleteScreening(String title, String name, String time) {
        screeningService.deleteScreening(title);
    }

    @ShellMethod(key = "list screenings")
    public void listScreenings() {
        List<Screening> screenings= screeningService.listScreenings();
        if (screenings.isEmpty())
        {
            System.out.println("There are no screenings");
        }
        else {
            for (var e:screenings) {
                System.out.println(e.getMovie().getTitle() + "(" + e.getMovie().getCategory() + ", " + e.getMovie().getLength() + " minutes), screened in room " + e.getRoom().getName() + ", at " + e.getTime());
            }
        }
    }
}
