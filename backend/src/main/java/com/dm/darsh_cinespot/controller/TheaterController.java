package com.dm.darsh_cinespot.controller;

import com.dm.darsh_cinespot.dto.request.TheaterRequest;
import com.dm.darsh_cinespot.dto.response.TheaterResponse;
import com.dm.darsh_cinespot.service.TheaterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;

    // CREATE THEATER
    @PostMapping
    public ResponseEntity<TheaterResponse> createTheater(
            @Valid @RequestBody TheaterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(theaterService.createTheater(request));
    }

    // GET ALL THEATERS
    @GetMapping
    public ResponseEntity<List<TheaterResponse>> getAllTheaters() {

        return ResponseEntity.ok(
                theaterService.getAllTheaters()
        );
    }

    // GET THEATER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TheaterResponse> getTheaterById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                theaterService.getTheaterById(id)
        );
    }

    // GET THEATERS BY CITY
    @GetMapping("/city/{city}")
    public ResponseEntity<List<TheaterResponse>> findByCity(
            @PathVariable String city) {

        return ResponseEntity.ok(
                theaterService.findByCity(city)
        );
    }

    // UPDATE THEATER
    @PutMapping("/{id}")
    public ResponseEntity<TheaterResponse> updateTheater(
            @PathVariable Long id,
            @Valid @RequestBody TheaterRequest request) {

        return ResponseEntity.ok(
                theaterService.updateTheater(id, request)
        );
    }

    // DELETE THEATER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(
            @PathVariable Long id) {

        theaterService.deleteTheater(id);

        return ResponseEntity.noContent().build();
    }
}