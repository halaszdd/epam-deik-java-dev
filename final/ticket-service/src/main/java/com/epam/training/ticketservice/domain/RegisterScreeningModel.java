package com.epam.training.ticketservice.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterScreeningModel {

    private String title;
    private String name;
    private LocalDateTime time;
}
