package com.dm.darsh_cinespot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

    private Long id;

    private String title;

    private String description;

    private String language;

    private String genre;

    private Integer durationMinutes;

    private BigDecimal price;

    private String poster;
}