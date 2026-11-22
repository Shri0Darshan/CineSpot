package com.dm.darsh_cinespot.repository;

import com.dm.darsh_cinespot.model.ShowSeat;
import com.dm.darsh_cinespot.model.enums.ShowSeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShowId(Long showId);

    List<ShowSeat> findByShowIdAndStatus(
            Long showId,
            ShowSeatStatus status
    );

    Optional<ShowSeat> findByShowIdAndSeatId(
            Long showId,
            Long seatId
    );

    List<ShowSeat> findByShowIdAndSeatIdIn(
            Long showId,
            List<Long> seatIds
    );

    boolean existsByShowIdAndSeatId(
            Long showId,
            Long seatId
    );

    long countByShowIdAndStatus(
            Long showId,
            ShowSeatStatus status
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT ss
            FROM ShowSeat ss
            WHERE ss.id = :id
            """)
    Optional<ShowSeat> findByIdForUpdate(@Param("id") Long id);
}