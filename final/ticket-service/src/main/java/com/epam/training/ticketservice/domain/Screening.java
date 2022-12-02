package com.epam.training.ticketservice.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(ScreeningId.class)
public class Screening {

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Movie movie;
    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Room room;
    private LocalDateTime time;
}
