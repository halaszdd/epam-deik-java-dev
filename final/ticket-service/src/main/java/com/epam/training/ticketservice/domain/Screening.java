package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Screening {
    @Id
    private ScreeningId screeningId;
    @Column(nullable = true)
    private LocalDateTime insertedAt;
}
