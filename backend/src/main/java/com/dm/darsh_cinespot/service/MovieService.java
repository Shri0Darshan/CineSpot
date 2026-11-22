package com.dm.darsh_cinespot.service;

import com.dm.darsh_cinespot.dto.request.MovieRequest;
import com.dm.darsh_cinespot.dto.response.MovieResponse;
import com.dm.darsh_cinespot.exception.DuplicateResourceException;
import com.dm.darsh_cinespot.exception.ResourceNotFoundException;
import com.dm.darsh_cinespot.model.Movie;
import com.dm.darsh_cinespot.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieResponse createMovie(MovieRequest request) {

        if (movieRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new DuplicateResourceException(
                    "Movie already exists: " + request.getTitle()
            );
        }

        Movie movie = new Movie();

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setLanguage(request.getLanguage());
        movie.setGenre(request.getGenre());
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setPrice(request.getPrice());

        return mapToResponse(movieRepository.save(movie));
    }

    @Transactional(readOnly = true)
    public List<MovieResponse> getAllMovies() {

        return movieRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MovieResponse getMovieById(Long id) {

        return mapToResponse(findMovie(id));
    }

    @Transactional(readOnly = true)
    public List<MovieResponse> searchByTitle(String title) {

        return movieRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovieResponse> findByGenre(String genre) {

        return movieRepository
                .findByGenreIgnoreCase(genre)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovieResponse> findByLanguage(String language) {

        return movieRepository
                .findByLanguageIgnoreCase(language)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public MovieResponse updateMovie(
            Long id,
            MovieRequest request
    ) {

        Movie movie = findMovie(id);

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setLanguage(request.getLanguage());
        movie.setGenre(request.getGenre());
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setPrice(request.getPrice());
        movie.setPoster(request.getPoster());

        return mapToResponse(movieRepository.save(movie));
    }

    public void deleteMovie(Long id) {

        Movie movie = findMovie(id);

        movieRepository.delete(movie);
    }

    private Movie findMovie(Long id) {

        return movieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Movie not found with id: " + id
                        )
                );
    }

    private MovieResponse mapToResponse(Movie movie) {

        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getLanguage(),
                movie.getGenre(),
                movie.getDurationMinutes(),
                movie.getPrice(),
                movie.getPoster()
        );
    }
}