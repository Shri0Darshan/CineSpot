package com.dm.darsh_cinespot.controller;

import com.dm.darsh_cinespot.dto.request.ShowRequest;
import com.dm.darsh_cinespot.dto.response.ShowResponse;
import com.dm.darsh_cinespot.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    // CREATE SHOW
    @PostMapping
    public ResponseEntity<ShowResponse> createShow(
            @Valid @RequestBody ShowRequest request) {

        ShowResponse response = showService.createShow(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // GET ALL SHOWS
    @GetMapping
    public ResponseEntity<List<ShowResponse>> getAllShows() {

        return ResponseEntity.ok(
                showService.getAllShows()
        );
    }

    // GET SHOW BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ShowResponse> getShowById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                showService.getShowById(id)
        );
    }

    // UPDATE SHOW
    @PutMapping("/{id}")
    public ResponseEntity<ShowResponse> updateShow(
            @PathVariable Long id,
            @Valid @RequestBody ShowRequest request) {

        return ResponseEntity.ok(
                showService.updateShow(id, request)
        );
    }

    // DELETE SHOW
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(
            @PathVariable Long id) {

        showService.deleteShow(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowResponse>> getShowsByMovieId(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                showService.getShowsByMovie(movieId)
        );
    }



}