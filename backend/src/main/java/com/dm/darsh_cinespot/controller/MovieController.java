package com.dm.darsh_cinespot.controller;

import com.dm.darsh_cinespot.dto.request.MovieRequest;
import com.dm.darsh_cinespot.dto.response.MovieResponse;
import com.dm.darsh_cinespot.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // CREATE MOVIE
    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(
            @Valid @RequestBody MovieRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieService.createMovie(request));
    }

    // GET ALL MOVIES
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {

        return ResponseEntity.ok(
                movieService.getAllMovies()
        );
    }

    // GET MOVIE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                movieService.getMovieById(id)
        );
    }

    // SEARCH MOVIE BY TITLE
    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> searchByTitle(
            @RequestParam String title) {

        return ResponseEntity.ok(
                movieService.searchByTitle(title)
        );
    }

    // GET MOVIES BY GENRE
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieResponse>> findByGenre(
            @PathVariable String genre) {

        return ResponseEntity.ok(
                movieService.findByGenre(genre)
        );
    }

    // GET MOVIES BY LANGUAGE
    @GetMapping("/language/{language}")
    public ResponseEntity<List<MovieResponse>> findByLanguage(
            @PathVariable String language) {

        return ResponseEntity.ok(
                movieService.findByLanguage(language)
        );
    }

    // UPDATE MOVIE
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieRequest request) {

        return ResponseEntity.ok(
                movieService.updateMovie(id, request)
        );
    }

    // DELETE MOVIE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(
            @PathVariable Long id) {

        movieService.deleteMovie(id);

        return ResponseEntity.noContent().build();
    }
}