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
public class Movie {

    @Id
    private String title;
    private String category;
    private int length;
    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Screening> screenings;
}
