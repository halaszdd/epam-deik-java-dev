package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningId implements Serializable {

    private String movie;
    private String room;

}
