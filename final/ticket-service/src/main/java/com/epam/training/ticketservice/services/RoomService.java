package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void createRoom(Room room) {
        roomRepository.save(room);
        LOGGER.info("Room created: {}", room);
    }

    @Transactional
    public void updateRoom(Room room) {
        Room room1 = roomRepository.findById(room.getName()).orElseThrow(() -> {
            throw new NoSuchElementException();
        });
        room1.setRows(room.getRows());
        room1.setColumns(room.getColumns());
        LOGGER.info("Room updated: {}", room1);
    }

    public void deleteRoom(String name) {
        Room room = roomRepository.findById(name).orElseThrow(() -> {
            throw new NoSuchElementException();
        });
        roomRepository.delete(room);
        LOGGER.info("Movie deleted: {}", room);
    }

    public List<Room> listRooms() {
        return roomRepository.findAll();
    }
}
