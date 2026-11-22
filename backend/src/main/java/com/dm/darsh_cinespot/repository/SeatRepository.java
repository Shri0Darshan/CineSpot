package com.dm.darsh_cinespot.repository;

import com.dm.darsh_cinespot.model.Seat;
import com.dm.darsh_cinespot.model.enums.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByScreenId(Long screenId);

    List<Seat> findByScreenIdAndSeatType(
            Long screenId,
            SeatType seatType
    );

    boolean existsByScreenIdAndSeatNumber(
            Long screenId,
            String seatNumber
    );
}