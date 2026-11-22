package com.dm.darsh_cinespot.service;

import com.dm.darsh_cinespot.dto.request.BookingRequest;
import com.dm.darsh_cinespot.dto.response.BookingResponse;
import com.dm.darsh_cinespot.exception.InvalidBookingException;
import com.dm.darsh_cinespot.exception.ResourceNotFoundException;
import com.dm.darsh_cinespot.exception.SeatNotAvailableException;
import com.dm.darsh_cinespot.model.*;
import com.dm.darsh_cinespot.model.enums.BookingStatus;
import com.dm.darsh_cinespot.model.enums.ShowSeatStatus;
import com.dm.darsh_cinespot.repository.BookingRepository;
import com.dm.darsh_cinespot.repository.ShowSeatRepository;
import com.dm.darsh_cinespot.repository.ShowRepository;
import com.dm.darsh_cinespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;

    @Transactional
    public BookingResponse createBooking(
            BookingRequest request
    ) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: "
                                        + request.getUserId()
                        )
                );

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: "
                                        + request.getShowId()
                        )
                );

        if (!show.getStartTime().isAfter(LocalDateTime.now())) {
            throw new InvalidBookingException(
                    "Cannot book a show that has already started"
            );
        }

        if (request.getShowSeatIds() == null
                || request.getShowSeatIds().isEmpty()) {

            throw new InvalidBookingException(
                    "At least one seat must be selected"
            );
        }

        List<Long> requestedIds = request.getShowSeatIds();

        if (requestedIds.stream().distinct().count()
                != requestedIds.size()) {

            throw new InvalidBookingException(
                    "Duplicate seat IDs are not allowed"
            );
        }

        List<ShowSeat> selectedSeats = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Long showSeatId : requestedIds) {

            /*
             * Pessimistic lock.
             * Prevents two users from booking the same seat
             * at the same time.
             */
            ShowSeat showSeat =
                    showSeatRepository.findByIdForUpdate(showSeatId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Show seat not found with id: "
                                                    + showSeatId
                                    )
                            );

            if (!showSeat.getShow().getId()
                    .equals(show.getId())) {

                throw new SeatNotAvailableException(
                        "Seat does not belong to this show: "
                                + showSeat.getSeat().getSeatNumber()
                );
            }

            if (showSeat.getStatus()
                    != ShowSeatStatus.AVAILABLE) {

                throw new SeatNotAvailableException(
                        "Seat "
                                + showSeat.getSeat().getSeatNumber()
                                + " is not available. Current status: "
                                + showSeat.getStatus()
                );
            }

            selectedSeats.add(showSeat);

            totalAmount = totalAmount.add(
                    showSeat.getPrice()
            );
        }

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setShow(show);
        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setBookingTime(LocalDateTime.now());

        Booking savedBooking =
                bookingRepository.save(booking);

        for (ShowSeat showSeat : selectedSeats) {

            showSeat.setStatus(
                    ShowSeatStatus.BOOKED
            );

            showSeat.setBooking(savedBooking);

            showSeatRepository.save(showSeat);
        }

        savedBooking.setShowSeats(selectedSeats);

        return mapToResponse(savedBooking);
    }

    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long id) {

        Booking booking = findBooking(id);

        return mapToResponse(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByUser(
            Long userId
    ) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId
            );
        }

        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public BookingResponse cancelBooking(Long id) {

        Booking booking = findBooking(id);

        if (booking.getStatus()
                == BookingStatus.CANCELLED) {

            throw new InvalidBookingException(
                    "Booking is already cancelled"
            );
        }

        if (booking.getShow().getStartTime()
                .isBefore(LocalDateTime.now())) {

            throw new InvalidBookingException(
                    "Cannot cancel a booking after the show has started"
            );
        }

        booking.setStatus(
                BookingStatus.CANCELLED
        );

        if (booking.getShowSeats() != null) {

            for (ShowSeat showSeat :
                    booking.getShowSeats()) {

                showSeat.setStatus(
                        ShowSeatStatus.AVAILABLE
                );

                showSeat.setBooking(null);

                showSeatRepository.save(showSeat);
            }
        }

        return mapToResponse(
                bookingRepository.save(booking)
        );
    }

    private Booking findBooking(Long id) {

        return bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + id
                        )
                );
    }

    private BookingResponse mapToResponse(
            Booking booking
    ) {

        List<String> seatNumbers =
                booking.getShowSeats() == null
                        ? List.of()
                        : booking.getShowSeats()
                        .stream()
                        .map(ss ->
                                ss.getSeat().getSeatNumber()
                        )
                        .toList();

        return new BookingResponse(
                booking.getId(),
                booking.getUser().getId(),
                booking.getUser().getName(),
                booking.getShow().getId(),
                booking.getShow().getMovie().getTitle(),
                booking.getShow().getScreen()
                        .getTheater().getName(),
                booking.getShow().getScreen().getScreenName(),
                seatNumbers,
                booking.getTotalAmount(),
                booking.getStatus(),
                booking.getBookingTime(),
                booking.getPayment() != null
                        ? booking.getPayment().getId()
                        : null
        );
    }
}