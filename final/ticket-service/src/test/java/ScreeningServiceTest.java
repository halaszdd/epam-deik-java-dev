import com.epam.training.ticketservice.domain.*;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.repositories.ScreeningRepository;
import com.epam.training.ticketservice.services.ScreeningOverlapException;
import com.epam.training.ticketservice.services.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreeningServiceTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private MovieRepository movieRepository;

    private ScreeningService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ScreeningService(screeningRepository, movieRepository, roomRepository);
    }

    @Test
    void createScreeningShouldReturnCorrectly() {
        //given
        RegisterScreeningModel registerScreeningModel = new RegisterScreeningModel();
        Movie movie = new Movie();
        Room room = new Room();
        when(movieRepository.findById(registerScreeningModel.getTitle())).thenReturn(Optional.of(movie));
        when(roomRepository.findById(registerScreeningModel.getName())).thenReturn(Optional.of(room));
        when(screeningRepository.findFirstBefore(room.getName(), registerScreeningModel.getTime())).thenReturn(Optional.empty());
        when(screeningRepository.findFirstAfter(room.getName(), registerScreeningModel.getTime())).thenReturn(Optional.empty());
        //when
        underTest.createScreening(registerScreeningModel);
        //then
        verify(screeningRepository).save(any(Screening.class));
    }

    @Test
    void createScreeningShouldThrowWhenOverlappingWithNext() {
        //given
        LocalDateTime time = LocalDateTime.of(2000, 11, 11, 11, 11);
        RegisterScreeningModel registerScreeningModel = new RegisterScreeningModel();
        registerScreeningModel.setTime(time);
        Movie movie = new Movie("title", "drama", 450, null);
        Room room = new Room();
        Screening screening = new Screening(ScreeningId.builder().movie(movie).room(room).time(time).build(), null);
        when(movieRepository.findById(registerScreeningModel.getTitle())).thenReturn(Optional.of(movie));
        when(roomRepository.findById(registerScreeningModel.getName())).thenReturn(Optional.of(room));
        when(screeningRepository.findFirstBefore(room.getName(), registerScreeningModel.getTime())).thenReturn(Optional.empty());
        when(screeningRepository.findFirstAfter(room.getName(), registerScreeningModel.getTime())).thenReturn(Optional.of(screening));
        //when
        //then
        assertThrows(ScreeningOverlapException.class, () -> {underTest.createScreening(registerScreeningModel);});
    }

    @Test
    void listScreenings() {
        //given

        //when

        //then

    }

    @Test
    void deleteScreening() {
    }
}