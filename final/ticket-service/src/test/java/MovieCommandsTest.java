import com.epam.training.ticketservice.commands.MovieCommands;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.services.AuthenticationService;
import com.epam.training.ticketservice.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieCommandsTest {

    private MovieCommands underTest;

    @Mock
    private MovieService movieService;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() { underTest = new MovieCommands(authenticationService, movieService); }


    @Test
    void createMovie() {
        //given
        Movie movie = new Movie();
        //when
        underTest.createMovie(movie.getTitle(),movie.getCategory(),movie.getLength());
        //then
        verify(movieService).createMovie(movie);
    }

    @Test
    void updateMovie() {
    }

    @Test
    void deleteMovie() {
    }

    @Test
    void listMovies() {
        //given
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();
        when(movieService.listMovies()).thenReturn(List.of(movie1,movie2));
        //when
        underTest.listMovies();
        //then
    }
}