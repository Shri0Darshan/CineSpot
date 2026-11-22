package com.dm.darsh_cinespot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterResponse {

    private Long id;

    private String name;

    private String address;

    private String city;
}
