package com.dm.darsh_cinespot.dto.response;

import com.dm.darsh_cinespot.model.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;

    private Long userId;

    private String userName;

    private Long showId;

    private String movieTitle;

    private String theaterName;

    private String screenName;

    private List<String> seatNumbers;

    private BigDecimal totalAmount;

    private BookingStatus status;

    private LocalDateTime bookingTime;

    private Long paymentId;
}