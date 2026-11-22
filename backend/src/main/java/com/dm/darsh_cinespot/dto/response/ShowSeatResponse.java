package com.dm.darsh_cinespot.dto.response;

import com.dm.darsh_cinespot.model.enums.SeatType;
import com.dm.darsh_cinespot.model.enums.ShowSeatStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatResponse {

    private Long id;

    private Long showId;

    private Long seatId;

    private String seatNumber;

    private SeatType seatType;

    private ShowSeatStatus status;

    private BigDecimal price;

    private Long bookingId;
}