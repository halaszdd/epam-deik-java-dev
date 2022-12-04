import com.epam.training.ticketservice.commands.RoomCommands;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.services.AuthenticationService;
import com.epam.training.ticketservice.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RoomCommandsTest {

    private RoomCommands underTest;
    @Mock
    private RoomService roomService;
    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() { underTest = new RoomCommands(authenticationService, roomService); }

    @Test
    void createRoom() {
        //given
        Room room = new Room();
        //when
        underTest.createRoom(room.getName(),room.getRows(),room.getColumns());
        //then
        verify(roomService).createRoom(room);
    }

    @Test
    void updateRoom() {
    }

    @Test
    void deleteRoom() {
    }

    @Test
    void listRooms() {
        //given
        Room room1 = new Room();
        Room room2 = new Room();
        when(roomService.listRooms()).thenReturn(List.of(room1, room2));
        //when
        underTest.listRooms();
        //then
    }
}