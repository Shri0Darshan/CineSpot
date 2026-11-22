package com.dm.darsh_cinespot.service;

import com.dm.darsh_cinespot.dto.request.PaymentRequest;
import com.dm.darsh_cinespot.dto.response.PaymentResponse;
import com.dm.darsh_cinespot.exception.DuplicateResourceException;
import com.dm.darsh_cinespot.exception.InvalidPaymentException;
import com.dm.darsh_cinespot.exception.ResourceNotFoundException;
import com.dm.darsh_cinespot.model.Booking;
import com.dm.darsh_cinespot.model.Payment;
import com.dm.darsh_cinespot.model.enums.BookingStatus;
import com.dm.darsh_cinespot.model.enums.PaymentStatus;
import com.dm.darsh_cinespot.repository.BookingRepository;
import com.dm.darsh_cinespot.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentResponse createPayment(
            PaymentRequest request
    ) {

        Booking booking = bookingRepository
                .findById(request.getBookingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: "
                                        + request.getBookingId()
                        )
                );

        if (booking.getStatus()
                == BookingStatus.CANCELLED) {

            throw new InvalidPaymentException(
                    "Cannot make payment for a cancelled booking"
            );
        }

        if (paymentRepository
                .findByBookingId(request.getBookingId())
                .isPresent()) {

            throw new DuplicateResourceException(
                    "Payment already exists for booking: "
                            + request.getBookingId()
            );
        }

        if (request.getAmount()
                .compareTo(booking.getTotalAmount()) != 0) {

            throw new InvalidPaymentException(
                    "Payment amount does not match booking amount. "
                            + "Expected: "
                            + booking.getTotalAmount()
                            + ", Received: "
                            + request.getAmount()
            );
        }

        Payment payment = new Payment();

        payment.setBooking(booking);
        payment.setAmount(booking.getTotalAmount());
        payment.setPaymentMethod(
                request.getPaymentMethod()
        );

        /*
         * Mock payment processing.
         * In a real application this will call
         * Razorpay/Stripe/etc.
         */
        payment.setPaymentStatus(
                PaymentStatus.SUCCESS
        );

        payment.setTransactionId(
                "TXN-" + UUID.randomUUID()
        );

        payment.setPaymentTime(
                LocalDateTime.now()
        );

        return mapToResponse(
                paymentRepository.save(payment)
        );
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id
                        )
                );

        return mapToResponse(payment);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByBookingId(
            Long bookingId
    ) {

        Payment payment =
                paymentRepository.findByBookingId(bookingId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found for booking: "
                                                + bookingId
                                )
                        );

        return mapToResponse(payment);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PaymentResponse refundPayment(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id
                        )
                );

        if (payment.getPaymentStatus()
                != PaymentStatus.SUCCESS) {

            throw new InvalidPaymentException(
                    "Only successful payments can be refunded"
            );
        }

        payment.setPaymentStatus(
                PaymentStatus.REFUNDED
        );

        return mapToResponse(
                paymentRepository.save(payment)
        );
    }

    private PaymentResponse mapToResponse(
            Payment payment
    ) {

        return new PaymentResponse(
                payment.getId(),
                payment.getBooking().getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTransactionId(),
                payment.getPaymentTime()
        );
    }
}