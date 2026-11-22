package com.dm.darsh_cinespot.controller;

import com.dm.darsh_cinespot.dto.request.ScreenRequest;
import com.dm.darsh_cinespot.dto.response.ScreenResponse;
import com.dm.darsh_cinespot.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    // CREATE SCREEN
    @PostMapping
    public ResponseEntity<ScreenResponse> createScreen(
            @Valid @RequestBody ScreenRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(screenService.createScreen(request));
    }

    // GET ALL SCREENS
    @GetMapping
    public ResponseEntity<List<ScreenResponse>> getAllScreens() {

        return ResponseEntity.ok(
                screenService.getAllScreens()
        );
    }

    // GET SCREEN BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ScreenResponse> getScreenById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                screenService.getScreenById(id)
        );
    }

    // GET SCREENS BY THEATER
    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<ScreenResponse>> getScreensByTheater(
            @PathVariable Long theaterId) {

        return ResponseEntity.ok(
                screenService.getScreensByTheater(theaterId)
        );
    }

    // UPDATE SCREEN
    @PutMapping("/{id}")
    public ResponseEntity<ScreenResponse> updateScreen(
            @PathVariable Long id,
            @Valid @RequestBody ScreenRequest request) {

        return ResponseEntity.ok(
                screenService.updateScreen(id, request)
        );
    }

    // DELETE SCREEN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreen(
            @PathVariable Long id) {

        screenService.deleteScreen(id);

        return ResponseEntity.noContent().build();
    }
}