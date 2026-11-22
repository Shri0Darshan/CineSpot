package com.dm.darsh_cinespot.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotNull(message = "Show ID is required")
    @Positive(message = "Show ID must be positive")
    private Long showId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<
            @NotNull(message = "Seat ID cannot be null")
            @Positive(message = "Seat ID must be positive")
                    Long
            > showSeatIds;
}