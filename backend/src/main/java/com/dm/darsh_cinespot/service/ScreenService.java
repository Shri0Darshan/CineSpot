package com.dm.darsh_cinespot.service;

import com.dm.darsh_cinespot.dto.request.ScreenRequest;
import com.dm.darsh_cinespot.dto.response.ScreenResponse;
import com.dm.darsh_cinespot.exception.DuplicateResourceException;
import com.dm.darsh_cinespot.exception.ResourceNotFoundException;
import com.dm.darsh_cinespot.model.Screen;
import com.dm.darsh_cinespot.model.Theater;
import com.dm.darsh_cinespot.repository.ScreenRepository;
import com.dm.darsh_cinespot.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheaterRepository theaterRepository;

    public ScreenResponse createScreen(ScreenRequest request) {

        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theater not found with id: "
                                        + request.getTheaterId()
                        )
                );

        if (screenRepository.existsByTheaterIdAndScreenName(
                request.getTheaterId(),
                request.getScreenName())) {

            throw new DuplicateResourceException(
                    "Screen already exists in this theater"
            );
        }

        Screen screen = new Screen();

        screen.setScreenName(request.getScreenName());
        screen.setTotalSeats(request.getTotalSeats());
        screen.setTheater(theater);

        return mapToResponse(screenRepository.save(screen));
    }

    @Transactional(readOnly = true)
    public List<ScreenResponse> getAllScreens() {

        return screenRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScreenResponse getScreenById(Long id) {

        return mapToResponse(findScreen(id));
    }

    @Transactional(readOnly = true)
    public List<ScreenResponse> getScreensByTheater(Long theaterId) {

        if (!theaterRepository.existsById(theaterId)) {
            throw new ResourceNotFoundException(
                    "Theater not found with id: " + theaterId
            );
        }

        return screenRepository.findByTheaterId(theaterId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ScreenResponse updateScreen(
            Long id,
            ScreenRequest request
    ) {

        Screen screen = findScreen(id);

        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theater not found with id: "
                                        + request.getTheaterId()
                        )
                );

        screen.setScreenName(request.getScreenName());
        screen.setTotalSeats(request.getTotalSeats());
        screen.setTheater(theater);

        return mapToResponse(screenRepository.save(screen));
    }

    public void deleteScreen(Long id) {

        Screen screen = findScreen(id);

        screenRepository.delete(screen);
    }

    private Screen findScreen(Long id) {

        return screenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found with id: " + id
                        )
                );
    }

    private ScreenResponse mapToResponse(Screen screen) {

        return new ScreenResponse(
                screen.getId(),
                screen.getScreenName(),
                screen.getTotalSeats(),
                screen.getTheater().getId(),
                screen.getTheater().getName()
        );
    }
}