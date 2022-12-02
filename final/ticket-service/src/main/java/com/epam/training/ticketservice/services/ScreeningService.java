package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.RegisterScreeningModel;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.repositories.ScreeningRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningService.class);

    private final ScreeningRepository screeningRepository;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    public ScreeningService(ScreeningRepository screeningRepository, MovieRepository movieRepository, RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    public void createScreening(RegisterScreeningModel registerScreeningModel) {
        final var screeningBuilder = Screening.builder();

        final var movie = movieRepository.findById(registerScreeningModel.getTitle()).orElseThrow(
                () -> {
                    throw new NoSuchElementException();
                });

        final var room = roomRepository.findById(registerScreeningModel.getName()).orElseThrow(
                () -> {
                    throw new NoSuchElementException();
                });

        final var screening = screeningBuilder.movie(movie).room(room).time(registerScreeningModel.getTime()).build();

        var screeningBefore = screeningRepository.findFirstBefore(room.getName(), registerScreeningModel.getTime());
        //LOGGER.info("Screening before: {}", screeningBefore);

        screeningBefore.ifPresent(s -> {
            if (screening.getTime().isBefore(s.getTime().plusMinutes(s.getMovie().getLength()))) {
                throw new ScreeningOverlapException("Overlapping screening found: " + screeningBefore);
            }
            if (screening.getTime().isBefore(s.getTime().plusMinutes(10).plusMinutes(s.getMovie().getLength()))) {
                throw new ScreeningBreaktimeOverlapException("Overlapping screening during break: " + screeningBefore);
            }
        });

        var screeningAfter = screeningRepository.findFirstAfter(room.getName(), registerScreeningModel.getTime());
        //LOGGER.info("Screening before: {}", screeningAfter);

        screeningAfter.ifPresent(s -> {
            if (screening.getTime().plusMinutes(screening.getMovie().getLength()).isAfter(s.getTime())) {
                throw new ScreeningOverlapException("Overlapping screening found: " + screeningBefore);
            }
            if (screening.getTime().plusMinutes(screening.getMovie().getLength()).isAfter(s.getTime().minusMinutes(10))) {
                throw new ScreeningBreaktimeOverlapException("Overlapping screening during break: " + screeningBefore);
            }
        });
        screeningRepository.save(screening);
        //LOGGER.info("Screening created: {}", screening);
    }

    public void deleteScreening(String title, String name, String time) {
        Screening screening = screeningRepository.findById(title).orElseThrow(()
                -> {throw new NoSuchElementException();});
        screeningRepository.delete(screening);
        LOGGER.info("Screening deleted: {}",screening);
    }

    public List<Screening> listScreenings() {
        return screeningRepository.findAll().stream().peek(Screening::getMovie).collect(Collectors.toList());
    }
}
