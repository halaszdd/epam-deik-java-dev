import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.services.MovieService;
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
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    private MovieService underTest;

    @BeforeEach
    void setUp() {
        underTest = new MovieService(movieRepository);
    }

    @Test
    void createMovie() {
        // given
        Movie movie = new Movie();
        // when
        underTest.createMovie(movie);
        // then
        verify(movieRepository).save(movie);
    }

    @Test
    void updateMovieShouldReturnCorrectly() {
        // given
        Movie inputMovie = new Movie("title", "sci-fi", 500, null);
        Movie existingMovie = new Movie("title", "drama", 450, null);
        Movie expectedMovie = new Movie("title", "sci-fi", 500, null);
        when(movieRepository.findById(inputMovie.getTitle())).thenReturn(Optional.of(existingMovie));
        // when
        underTest.updateMovie(inputMovie);
        // then
        assertEquals(expectedMovie, existingMovie);
    }

    @Test
    void updateMovieShouldThrowWhenDoesntExist() {
        //given
        Movie inputMovie = new Movie("title", "sci-fi", 500, null);
        when(movieRepository.findById(inputMovie.getTitle())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> {
            underTest.updateMovie(inputMovie);
        });
    }

    @Test
    void deleteMovieShouldReturnCorrectly() {
        // given
        String movieTitle = "title";
        Movie existingMovie = new Movie(movieTitle, "drama", 450, null);
        when(movieRepository.findById(movieTitle)).thenReturn(Optional.of(existingMovie));
        // when
        underTest.deleteMovie(movieTitle);
        // then
        verify(movieRepository).delete(existingMovie);
    }

    @Test
    void listMovies() {
        //given
        Movie movie1 = new Movie("title","drama",450,null);
        Movie movie2 = new Movie("title2","sci-fi",500,null);
        when(movieRepository.findAll()).thenReturn(List.of(movie1,movie2));
        //when
        var actual = underTest.listMovies();
        //then
        assertEquals(List.of(movie1,movie2), actual);
    }
}