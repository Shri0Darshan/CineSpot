package com.dm.darsh_cinespot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenResponse {

    private Long id;

    private String screenName;

    private Integer totalSeats;

    private Long theaterId;

    private String theaterName;
}