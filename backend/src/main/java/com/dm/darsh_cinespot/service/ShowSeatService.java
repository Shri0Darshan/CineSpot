package com.dm.darsh_cinespot.service;

import com.dm.darsh_cinespot.dto.request.ShowSeatRequest;
import com.dm.darsh_cinespot.dto.response.ShowSeatResponse;
import com.dm.darsh_cinespot.exception.DuplicateResourceException;
import com.dm.darsh_cinespot.exception.ResourceNotFoundException;
import com.dm.darsh_cinespot.model.Seat;
import com.dm.darsh_cinespot.model.Show;
import com.dm.darsh_cinespot.model.ShowSeat;
import com.dm.darsh_cinespot.model.enums.ShowSeatStatus;
import com.dm.darsh_cinespot.repository.SeatRepository;
import com.dm.darsh_cinespot.repository.ShowRepository;
import com.dm.darsh_cinespot.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public ShowSeatResponse createShowSeat(
            ShowSeatRequest request
    ) {

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: "
                                        + request.getShowId()
                        )
                );

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seat not found with id: "
                                        + request.getSeatId()
                        )
                );

        if (!seat.getScreen().getId().equals(show.getScreen().getId())) {
            throw new IllegalArgumentException(
                    "Seat does not belong to the show's screen"
            );
        }

        if (showSeatRepository.existsByShowIdAndSeatId(
                request.getShowId(),
                request.getSeatId())) {

            throw new DuplicateResourceException(
                    "This seat is already configured for this show"
            );
        }

        ShowSeat showSeat = new ShowSeat();

        showSeat.setShow(show);
        showSeat.setSeat(seat);
        showSeat.setStatus(
                request.getStatus() == null
                        ? ShowSeatStatus.AVAILABLE
                        : request.getStatus()
        );
        showSeat.setPrice(request.getPrice());

        return mapToResponse(
                showSeatRepository.save(showSeat)
        );
    }

    @Transactional(readOnly = true)
    public List<ShowSeatResponse> getShowSeats(Long showId) {

        if (!showRepository.existsById(showId)) {
            throw new ResourceNotFoundException(
                    "Show not found with id: " + showId
            );
        }

        return showSeatRepository.findByShowId(showId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ShowSeatResponse> getAvailableSeats(Long showId) {

        if (!showRepository.existsById(showId)) {
            throw new ResourceNotFoundException(
                    "Show not found with id: " + showId
            );
        }

        return showSeatRepository.findByShowIdAndStatus(
                        showId,
                        ShowSeatStatus.AVAILABLE
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ShowSeatResponse getShowSeatById(Long id) {

        ShowSeat showSeat = showSeatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ShowSeat not found with id: " + id
                        )
                );

        return mapToResponse(showSeat);
    }

    public ShowSeatResponse updateStatus(
            Long id,
            ShowSeatStatus status
    ) {

        ShowSeat showSeat = showSeatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ShowSeat not found with id: " + id
                        )
                );

        showSeat.setStatus(status);

        return mapToResponse(
                showSeatRepository.save(showSeat)
        );
    }

    private ShowSeatResponse mapToResponse(
            ShowSeat showSeat
    ) {

        return new ShowSeatResponse(
                showSeat.getId(),
                showSeat.getShow().getId(),
                showSeat.getSeat().getId(),
                showSeat.getSeat().getSeatNumber(),
                showSeat.getSeat().getSeatType(),
                showSeat.getStatus(),
                showSeat.getPrice(),
                showSeat.getBooking() != null
                        ? showSeat.getBooking().getId()
                        : null
        );
    }
}