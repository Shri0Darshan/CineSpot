package com.dm.darsh_cinespot.repository;

import com.dm.darsh_cinespot.model.Booking;
import com.dm.darsh_cinespot.model.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByShowId(Long showId);

    List<Booking> findByUserIdAndStatus(
            Long userId,
            BookingStatus status
    );

    List<Booking> findByShowIdAndStatus(
            Long showId,
            BookingStatus status
    );

    long countByShowIdAndStatus(
            Long showId,
            BookingStatus status
    );
}