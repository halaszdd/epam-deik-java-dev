package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.services.AuthenticationService;
import com.epam.training.ticketservice.services.RoomService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
public class RoomCommands extends SecuredCommands {

    private final RoomService roomService;

    public RoomCommands(AuthenticationService authenticationService, RoomService roomService) {
        super(authenticationService);
        this.roomService = roomService;
    }

    @ShellMethod(key = "create room")
    @ShellMethodAvailability(value = "isAdmin")
    public void createRoom(String name, int row, int col) {
        var room = Room.builder().name(name).rows(row).columns(col).build();
        roomService.createRoom(room);
    }

    @ShellMethod(key = "update room")
    @ShellMethodAvailability(value = "isAdmin")
    public void updateRoom(String name, int row, int col) {
        var room = Room.builder().name(name).rows(row).columns(col).build();
        roomService.updateRoom(room);
    }

    @ShellMethod(key = "delete room")
    @ShellMethodAvailability(value = "isAdmin")
    public void deleteRoom(String name) {
        roomService.deleteRoom(name);
    }

    @ShellMethod(key = "list rooms")
    public void listRooms() {
        List<Room> rooms = roomService.listRooms();
        if (rooms.isEmpty()) {
            System.out.println("There are no rooms at the moment");
        } else {
            for (var e : rooms) {
                System.out.println("Room " + e.getName() + " with "
                        + e.getRows() * e.getColumns() + " seats, " + e.getRows()
                        + " rows and " + e.getColumns() + " columns");
            }
        }
    }
}
