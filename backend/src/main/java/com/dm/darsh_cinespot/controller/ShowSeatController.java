package com.dm.darsh_cinespot.controller;

import com.dm.darsh_cinespot.dto.request.ShowSeatRequest;
import com.dm.darsh_cinespot.dto.response.ShowSeatResponse;
import com.dm.darsh_cinespot.model.enums.ShowSeatStatus;
import com.dm.darsh_cinespot.service.ShowSeatService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show-seats")
@RequiredArgsConstructor
@Validated
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    // CREATE SHOW SEAT
    @PostMapping
    public ResponseEntity<ShowSeatResponse> createShowSeat(
            @Valid @RequestBody ShowSeatRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(showSeatService.createShowSeat(request));
    }

    // GET SHOW SEAT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ShowSeatResponse> getShowSeatById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                showSeatService.getShowSeatById(id)
        );
    }

    // GET ALL SEATS FOR A SHOW
    @GetMapping("/show/{showId}")
    public ResponseEntity<List<ShowSeatResponse>> getShowSeats(
            @PathVariable Long showId) {

        return ResponseEntity.ok(
                showSeatService.getShowSeats(showId)
        );
    }

    // GET AVAILABLE SEATS
    @GetMapping("/show/{showId}/available")
    public ResponseEntity<List<ShowSeatResponse>> getAvailableSeats(
            @PathVariable Long showId) {

        return ResponseEntity.ok(
                showSeatService.getAvailableSeats(showId)
        );
    }

    // UPDATE SHOW SEAT STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<ShowSeatResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam @NotNull ShowSeatStatus status) {

        return ResponseEntity.ok(
                showSeatService.updateStatus(id, status)
        );
    }
}