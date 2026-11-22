package com.dm.darsh_cinespot.service;

import com.dm.darsh_cinespot.dto.request.ShowRequest;
import com.dm.darsh_cinespot.dto.response.ShowResponse;
import com.dm.darsh_cinespot.exception.InvalidBookingException;
import com.dm.darsh_cinespot.exception.ResourceNotFoundException;
import com.dm.darsh_cinespot.exception.ShowConflictException;
import com.dm.darsh_cinespot.model.Movie;
import com.dm.darsh_cinespot.model.Screen;
import com.dm.darsh_cinespot.model.Show;
import com.dm.darsh_cinespot.repository.MovieRepository;
import com.dm.darsh_cinespot.repository.ScreenRepository;
import com.dm.darsh_cinespot.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    public ShowResponse createShow(ShowRequest request) {

        validateShowTime(
                request.getStartTime(),
                request.getEndTime()
        );

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Movie not found with id: "
                                        + request.getMovieId()
                        )
                );

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found with id: "
                                        + request.getScreenId()
                        )
                );

        checkForConflict(
                screen.getId(),
                request.getStartTime(),
                request.getEndTime()
        );

        Show show = new Show();

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());

        return mapToResponse(showRepository.save(show));
    }

    @Transactional(readOnly = true)
    public List<ShowResponse> getAllShows() {

        return showRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ShowResponse getShowById(Long id) {

        return mapToResponse(findShow(id));
    }

    @Transactional(readOnly = true)
    public List<ShowResponse> getShowsByMovie(Long movieId) {

        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException(
                    "Movie not found with id: " + movieId
            );
        }

        return showRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ShowResponse> getShowsByScreen(Long screenId) {

        if (!screenRepository.existsById(screenId)) {
            throw new ResourceNotFoundException(
                    "Screen not found with id: " + screenId
            );
        }

        return showRepository.findByScreenId(screenId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ShowResponse updateShow(
            Long id,
            ShowRequest request
    ) {

        Show show = findShow(id);

        validateShowTime(
                request.getStartTime(),
                request.getEndTime()
        );

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Movie not found with id: "
                                        + request.getMovieId()
                        )
                );

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found with id: "
                                        + request.getScreenId()
                        )
                );

        List<Show> conflicts =
                showRepository.findOverlappingShowsExcludingCurrent(
                        screen.getId(),
                        show.getId(),
                        request.getStartTime(),
                        request.getEndTime()
                );

        if (!conflicts.isEmpty()) {
            throw new ShowConflictException(
                    "Another show is already scheduled on this screen"
            );
        }

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());

        return mapToResponse(showRepository.save(show));
    }

    public void deleteShow(Long id) {

        Show show = findShow(id);

        showRepository.delete(show);
    }

    private void validateShowTime(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        if (!endTime.isAfter(startTime)) {
            throw new InvalidBookingException(
                    "Show end time must be after start time"
            );
        }

        if (!startTime.isAfter(LocalDateTime.now())) {
            throw new InvalidBookingException(
                    "Show start time must be in the future"
            );
        }
    }

    private void checkForConflict(
            Long screenId,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        List<Show> conflicts =
                showRepository.findOverlappingShows(
                        screenId,
                        startTime,
                        endTime
                );

        if (!conflicts.isEmpty()) {
            throw new ShowConflictException(
                    "Another show is already scheduled on this screen "
                            + "during the selected time"
            );
        }
    }

    private Show findShow(Long id) {

        return showRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: " + id
                        )
                );
    }

    private ShowResponse mapToResponse(Show show) {

        return new ShowResponse(
                show.getId(),
                show.getMovie().getId(),
                show.getMovie().getTitle(),
                show.getScreen().getId(),
                show.getScreen().getScreenName(),
                show.getStartTime(),
                show.getEndTime()
        );
    }
}