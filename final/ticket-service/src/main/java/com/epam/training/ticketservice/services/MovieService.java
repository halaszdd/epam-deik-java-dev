package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void createMovie(Movie movie) {
        movieRepository.save(movie);
        LOGGER.info("Movie created: {}", movie);
    }

    @Transactional
    public void updateMovie(Movie movie) {
        Movie movie1 = movieRepository.findById(movie.getTitle()).orElseThrow(() -> {
            throw new NoSuchElementException();
        });
        movie1.setTitle(movie.getTitle());
        movie1.setCategory(movie.getCategory());
        movie1.setLength(movie.getLength());
        LOGGER.info("Movie updated: {}", movie1);
    }

    public void deleteMovie(String title) {
        Movie movie = movieRepository.findById(title).orElseThrow(() -> {
            throw new NoSuchElementException();
        });
        movieRepository.delete(movie);
        LOGGER.info("Movie deleted: {}", movie);
    }

    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }
}
