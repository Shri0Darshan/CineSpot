package com.dm.darsh_cinespot.dto.response;

import com.dm.darsh_cinespot.model.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {

    private Long id;

    private String seatNumber;

    private SeatType seatType;

    private Long screenId;

    private String screenName;
}