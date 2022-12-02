package com.epam.training.ticketservice.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ScreeningId implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Movie movie;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Room room;
    private LocalDateTime time;
}
