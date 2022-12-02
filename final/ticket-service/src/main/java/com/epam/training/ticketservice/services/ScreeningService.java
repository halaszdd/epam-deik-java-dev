package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.domain.RegisterScreeningModel;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.domain.ScreeningId;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.repositories.ScreeningRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Transactional
    public void createScreening(RegisterScreeningModel registerScreeningModel) {
        final var movie = movieRepository.findById(registerScreeningModel.getTitle()).orElseThrow(NoSuchElementException::new);
        final var room = roomRepository.findById(registerScreeningModel.getName()).orElseThrow(NoSuchElementException::new);

        final var screening = Screening.builder()
                .screeningId(ScreeningId.builder()
                        .movie(movie)
                        .room(room)
                        .time(registerScreeningModel.getTime())
                        .build())
                .build();

        var screeningBefore = screeningRepository.findFirstBefore(room.getName(), registerScreeningModel.getTime());

        screeningBefore.ifPresent(s -> {
            if (screening.getScreeningId().getTime().isBefore(s.getScreeningId().getTime().plusMinutes(s.getScreeningId().getMovie().getLength()))) {
                throw new ScreeningOverlapException("Overlapping screening found: " + screeningBefore);
            }
            if (screening.getScreeningId().getTime().isBefore(s.getScreeningId().getTime().plusMinutes(10).plusMinutes(s.getScreeningId().getMovie().getLength()))) {
                throw new ScreeningBreaktimeOverlapException("Overlapping screening during break: " + screeningBefore);
            }
        });

        var screeningAfter = screeningRepository.findFirstAfter(room.getName(), registerScreeningModel.getTime());

        screeningAfter.ifPresent(s -> {
            if (screening.getScreeningId().getTime().plusMinutes(screening.getScreeningId().getMovie().getLength()).isAfter(s.getScreeningId().getTime())) {
                throw new ScreeningOverlapException("Overlapping screening found: " + screeningBefore);
            }
            if (screening.getScreeningId().getTime().plusMinutes(screening.getScreeningId().getMovie().getLength()).isAfter(s.getScreeningId().getTime().minusMinutes(10))) {
                throw new ScreeningBreaktimeOverlapException("Overlapping screening during break: " + screeningBefore);
            }
        });
        screeningRepository.save(screening);
    }

    public List<Screening> listScreenings() {
        return screeningRepository.findAll(Sort.by(Sort.Direction.DESC, "insertedAt"));
    }

    public void deleteScreening(String movieName, String roomName, LocalDateTime time) {
        var movie = movieRepository.findById(movieName).orElseThrow();
        var room = roomRepository.findById(roomName).orElseThrow();
        screeningRepository.deleteById(ScreeningId.builder().movie(movie).room(room).time(time).build());
    }
}
