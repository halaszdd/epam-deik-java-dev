package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {

    @Id
    private String name;
    private int rows;
    private int columns;
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Screening> screenings;
}
