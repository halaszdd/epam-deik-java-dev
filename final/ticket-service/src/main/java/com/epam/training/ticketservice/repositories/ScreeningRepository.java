package com.epam.training.ticketservice.repositories;


import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.domain.ScreeningId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, ScreeningId> {

    @Query(value = "select screening " +
            "from Screening screening " +
            "where screening.screeningId.room.name=:roomName " +
            "and time<:date " +
            "order by time desc ")
    Optional<Screening> findFirstBefore(@Param("roomName") String roomName, @Param("date") LocalDateTime date);

    @Query(value = "select screening " +
            "from Screening screening " +
            "where screening.screeningId.room.name=:roomName " +
            "and time>:date " +
            "order by time desc ")
    Optional<Screening> findFirstAfter(@Param("roomName") String roomName, @Param("date") LocalDateTime date);
}
