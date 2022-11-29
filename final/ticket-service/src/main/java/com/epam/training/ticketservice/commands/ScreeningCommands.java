package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.domain.RegisterScreeningModel;
import com.epam.training.ticketservice.services.AuthenticationService;
import com.epam.training.ticketservice.services.ScreeningService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@ShellComponent
public class ScreeningCommands extends SecuredCommands {

    private final ScreeningService screeningService;

    public ScreeningCommands(AuthenticationService authenticationService, ScreeningService screeningService) {
        super(authenticationService);
        this.screeningService = screeningService;
    }

    @ShellMethod(key = "create screening")
    @ShellMethodAvailability(value = "isAdmin")
    public void createScreening(String title, String name,String time) {

        System.out.println(time);

        DateTimeFormatter DATE_FORMAT =
                new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd hh:mm")
                        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter();

        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime time2 = null;

        try {
            time2 = LocalDateTime.parse(time, formatter);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(time2);

        var screening = RegisterScreeningModel.builder()
                .title(title)
                .name(name)
                .time(time2)
                .build();
        screeningService.createScreening(screening);
    }
}
