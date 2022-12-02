package com.epam.training.ticketservice;

import com.epam.training.ticketservice.domain.Role;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.repositories.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestDataLoader {

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    public TestDataLoader(UserRepository userRepository,
                          MovieRepository movieRepository,
                          RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @PostConstruct
    public void init() {
        userRepository.save(User.builder().username("admin").password("admin").role(Role.ADMIN).build());
        //movieRepository.save(Movie.builder().title("Star Wars ROTS").category("sci-fi").length(160).build());
        //roomRepository.save(Room.builder().name("RoomNo1").rows(16).columns(16).build());
    }
}
