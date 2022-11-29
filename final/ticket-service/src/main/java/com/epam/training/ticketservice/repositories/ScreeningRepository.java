package com.epam.training.ticketservice.repositories;


import com.epam.training.ticketservice.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening,String> {

    @Query(value = "select * " +
            "from Screening " +
            "where room_name=:roomName " +
            "and time<:date " +
            "order by time desc ",
    nativeQuery = true)
    Optional<Screening> findFirstBefore(@Param("roomName") String roomName, @Param("date") LocalDateTime date);
}