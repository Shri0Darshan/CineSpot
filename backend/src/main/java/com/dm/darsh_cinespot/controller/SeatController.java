package com.dm.darsh_cinespot.controller;

import com.dm.darsh_cinespot.dto.request.SeatRequest;
import com.dm.darsh_cinespot.dto.response.SeatResponse;
import com.dm.darsh_cinespot.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    // CREATE SEAT
    @PostMapping
    public ResponseEntity<SeatResponse> createSeat(
            @Valid @RequestBody SeatRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(seatService.createSeat(request));
    }

    // GET ALL SEATS
    @GetMapping
    public ResponseEntity<List<SeatResponse>> getAllSeats() {

        return ResponseEntity.ok(
                seatService.getAllSeats()
        );
    }

    // GET SEAT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SeatResponse> getSeatById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                seatService.getSeatById(id)
        );
    }

    // GET SEATS BY SCREEN
    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<SeatResponse>> getSeatsByScreen(
            @PathVariable Long screenId) {

        return ResponseEntity.ok(
                seatService.getSeatsByScreen(screenId)
        );
    }

    // UPDATE SEAT
    @PutMapping("/{id}")
    public ResponseEntity<SeatResponse> updateSeat(
            @PathVariable Long id,
            @Valid @RequestBody SeatRequest request) {

        return ResponseEntity.ok(
                seatService.updateSeat(id, request)
        );
    }

    // DELETE SEAT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(
            @PathVariable Long id) {

        seatService.deleteSeat(id);

        return ResponseEntity.noContent().build();
    }
}