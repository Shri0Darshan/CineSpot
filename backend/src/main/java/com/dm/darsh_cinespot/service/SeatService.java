package com.dm.darsh_cinespot.service;

import com.dm.darsh_cinespot.dto.request.SeatRequest;
import com.dm.darsh_cinespot.dto.response.SeatResponse;
import com.dm.darsh_cinespot.exception.DuplicateResourceException;
import com.dm.darsh_cinespot.exception.ResourceNotFoundException;
import com.dm.darsh_cinespot.model.Screen;
import com.dm.darsh_cinespot.model.Seat;
import com.dm.darsh_cinespot.repository.ScreenRepository;
import com.dm.darsh_cinespot.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    public SeatResponse createSeat(SeatRequest request) {

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found with id: "
                                        + request.getScreenId()
                        )
                );

        if (seatRepository.existsByScreenIdAndSeatNumber(
                request.getScreenId(),
                request.getSeatNumber())) {

            throw new DuplicateResourceException(
                    "Seat " + request.getSeatNumber()
                            + " already exists in this screen"
            );
        }

        Seat seat = new Seat();

        seat.setSeatNumber(request.getSeatNumber());
        seat.setSeatType(request.getSeatType());
        seat.setScreen(screen);

        return mapToResponse(seatRepository.save(seat));
    }

    @Transactional(readOnly = true)
    public List<SeatResponse> getAllSeats() {

        return seatRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SeatResponse getSeatById(Long id) {

        return mapToResponse(findSeat(id));
    }

    @Transactional(readOnly = true)
    public List<SeatResponse> getSeatsByScreen(Long screenId) {

        if (!screenRepository.existsById(screenId)) {
            throw new ResourceNotFoundException(
                    "Screen not found with id: " + screenId
            );
        }

        return seatRepository.findByScreenId(screenId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public SeatResponse updateSeat(
            Long id,
            SeatRequest request
    ) {

        Seat seat = findSeat(id);

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found with id: "
                                        + request.getScreenId()
                        )
                );

        seat.setSeatNumber(request.getSeatNumber());
        seat.setSeatType(request.getSeatType());
        seat.setScreen(screen);

        return mapToResponse(seatRepository.save(seat));
    }

    public void deleteSeat(Long id) {

        Seat seat = findSeat(id);

        seatRepository.delete(seat);
    }

    private Seat findSeat(Long id) {

        return seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seat not found with id: " + id
                        )
                );
    }

    private SeatResponse mapToResponse(Seat seat) {

        return new SeatResponse(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getSeatType(),
                seat.getScreen().getId(),
                seat.getScreen().getScreenName()
        );
    }
}