package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.domain.RegisterScreeningModel;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.repositories.ScreeningRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ScreeningService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningService.class);

    private final ScreeningRepository screeningRepository;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    public ScreeningService(ScreeningRepository screeningRepository, MovieRepository movieRepository, RoomRepository roomRepository) {this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    public void createScreening(RegisterScreeningModel registerScreeningModel) {
        final var screeningBuilder = Screening.builder();

        final var movie = movieRepository.findById(registerScreeningModel.getTitle()).orElseThrow(
                () -> {throw new NoSuchElementException();});

        final var room = roomRepository.findById(registerScreeningModel.getName()).orElseThrow(
                () -> {throw new NoSuchElementException();});

        final var screening = screeningBuilder.movie(movie).room(room).time(registerScreeningModel.getTime()).build();

        LOGGER.info("Query : {}",screeningRepository.findFirstBefore(room.getName(),registerScreeningModel.getTime()));

        screeningRepository.save(screening);
        LOGGER.info("Screening created: {}",screening);
    }
}
