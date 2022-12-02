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

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ShellComponent
public class ScreeningCommands extends SecuredCommands {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final ScreeningService screeningService;

    public ScreeningCommands(AuthenticationService authenticationService, ScreeningService screeningService) {
        super(authenticationService);
        this.screeningService = screeningService;
    }

    @ShellMethod(key = "create screening")
    @ShellMethodAvailability(value = "isAdmin")
    public void createScreening(String title, String name, String time) {
        var screening = RegisterScreeningModel.builder()
                .title(title)
                .name(name)
                .time(LocalDateTime.parse(time, FORMATTER))
                .build();
        try {
            screeningService.createScreening(screening);
        } catch (ScreeningOverlapException overlapException) {
            System.out.println("There is an overlapping screening");
        } catch (ScreeningBreaktimeOverlapException breaktimeOverlapException) {
            System.out.println("This would start in the break period after another screening in this room");
        }
    }

    @ShellMethod(key = "list screenings")
    public void listScreenings() {
        List<Screening> screenings = screeningService.listScreenings();
        var listElemFormat = new MessageFormat("{0} ({1}, {2} minutes), screened in room {3}, at {4}");

        if (screenings.isEmpty()) {
            System.out.println("There are no screenings");
        } else {
            screenings.forEach(e -> {
                final var movie = e.getScreeningId().getMovie();
                final var room = e.getScreeningId().getRoom();
                System.out.println(listElemFormat.format(new Object[]{
                        movie.getTitle(),
                        movie.getCategory(),
                        movie.getLength(),
                        room.getName(),
                        FORMATTER.format(e.getScreeningId().getTime())}));
            });
        }
    }

    @ShellMethod(key = "delete screening")
    public void deleteScreening(String movieName, String roomName, String time) {
        screeningService.deleteScreening(movieName, roomName, LocalDateTime.parse(time, FORMATTER));
    }
}
