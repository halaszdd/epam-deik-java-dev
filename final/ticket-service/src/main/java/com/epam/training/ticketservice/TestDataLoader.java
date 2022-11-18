package com.epam.training.ticketservice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestDataLoader {

    private final UserRepository userRepository;

    public TestDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init(){
        userRepository.save(User.builder().username("admin").password("admin").role(Role.ADMIN).build());
    }
}
