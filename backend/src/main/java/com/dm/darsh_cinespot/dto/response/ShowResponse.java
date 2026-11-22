package com.dm.darsh_cinespot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponse {

    private Long id;

    private Long movieId;

    private String movieTitle;

    private Long screenId;

    private String screenName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}