import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    private RoomService underTest;

    @BeforeEach
    public void setUp() {
        underTest = new RoomService(roomRepository);
    }


    @Test
    public void createRoom() {
        // given
        Room room = new Room();
        // when
        underTest.createRoom(room);
        // then
        verify(roomRepository).save(room);
    }

    @Test
    public void updateRoomShouldReturnCorrectly() {
        // given
        Room inputRoom = new Room("valami", 15, 15, null);
        Room existingRoom = new Room("valami", 10, 10, null);
        Room expectedRoom = new Room("valami", 15, 15, null);
        when(roomRepository.findById(inputRoom.getName())).thenReturn(Optional.of(existingRoom));
        // when
        underTest.updateRoom(inputRoom);
        // then
        assertEquals(expectedRoom, existingRoom);
    }

    @Test
    public void updateRoomShouldThrowWhenDoesntExist() {
        //given
        Room inputRoom = new Room("valami", 15, 15, null);
        when(roomRepository.findById(inputRoom.getName())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> {
            underTest.updateRoom(inputRoom);
        });
    }

    @Test
    public void deleteRoomShouldReturnCorrectly() {
        // given
        String roomName = "valami";
        Room existingRoom = new Room(roomName, 10, 10, null);
        when(roomRepository.findById(roomName)).thenReturn(Optional.of(existingRoom));
        // when
        underTest.deleteRoom(roomName);
        // then
        verify(roomRepository).delete(existingRoom);
    }

    @Test
    public void listRooms() {
        // given
        Room room1 = new Room("ez nem valami", 10, 10, null);
        Room room2 = new Room("valami", 15, 15, null);
        when(roomRepository.findAll()).thenReturn(List.of(room1,room2));
        // when
        var actual = underTest.listRooms();
        // then
        assertEquals(List.of(room1,room2),actual);
    }
}