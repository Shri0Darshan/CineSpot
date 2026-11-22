package com.dm.darsh_cinespot.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenRequest {

    @NotBlank(message = "Screen name is required")
    @Size(max = 100, message = "Screen name cannot exceed 100 characters")
    private String screenName;

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    private Integer totalSeats;

    @NotNull(message = "Theater ID is required")
    @Positive(message = "Theater ID must be positive")
    private Long theaterId;
}