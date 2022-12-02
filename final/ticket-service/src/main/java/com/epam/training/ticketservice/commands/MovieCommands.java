package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.services.AuthenticationService;
import com.epam.training.ticketservice.services.MovieService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
public class MovieCommands extends SecuredCommands {

    private final MovieService movieService;

    public MovieCommands(AuthenticationService authenticationService, MovieService movieService) {
        super(authenticationService);
        this.movieService = movieService;
    }

    @ShellMethod(key = "create movie")
    @ShellMethodAvailability(value = "isAdmin")
    public void createMovie(String title, String category, int length) {
        var movie = Movie.builder().title(title).category(category).length(length).build();
        movieService.createMovie(movie);
    }

    @ShellMethod(key = "update movie")
    @ShellMethodAvailability(value = "isAdmin")
    public void updateMovie(String title, String category, int length) {
        var movie = Movie.builder().title(title).category(category).length(length).build();
        movieService.updateMovie(movie);
    }

    @ShellMethod(key = "delete movie")
    @ShellMethodAvailability(value = "isAdmin")
    public void deleteMovie(String title) {
        movieService.deleteMovie(title);
    }

    @ShellMethod(key = "list movies")
    public void listMovies() {
        List<Movie> movies = movieService.listMovies();
        if (movies.isEmpty()) {
            System.out.println("There are no movies at the moment");
        } else {
            for (var e : movies) {
                System.out.println(e.getTitle() + " " + "(" + e.getCategory() + ", " + e.getLength() + " minutes)");
            }
        }
    }
}
