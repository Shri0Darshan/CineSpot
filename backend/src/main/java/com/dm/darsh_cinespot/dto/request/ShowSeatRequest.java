package com.dm.darsh_cinespot.dto.request;

import com.dm.darsh_cinespot.model.enums.ShowSeatStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatRequest {

    @NotNull(message = "Show ID is required")
    @Positive(message = "Show ID must be positive")
    private Long showId;

    @NotNull(message = "Seat ID is required")
    @Positive(message = "Seat ID must be positive")
    private Long seatId;

    @NotNull(message = "Status is required")
    private ShowSeatStatus status;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
}