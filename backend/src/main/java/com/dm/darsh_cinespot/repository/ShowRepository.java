package com.dm.darsh_cinespot.repository;

import com.dm.darsh_cinespot.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovieId(Long movieId);

    List<Show> findByScreenId(Long screenId);

    List<Show> findByMovieIdAndStartTimeAfter(
            Long movieId,
            LocalDateTime startTime
    );

    List<Show> findByScreenIdAndStartTimeBetween(
            Long screenId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    @Query("""
            SELECT s
            FROM Show s
            WHERE s.screen.id = :screenId
            AND s.startTime < :endTime
            AND s.endTime > :startTime
            """)
    List<Show> findOverlappingShows(
            @Param("screenId") Long screenId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
            SELECT s
            FROM Show s
            WHERE s.screen.id = :screenId
            AND s.id <> :showId
            AND s.startTime < :endTime
            AND s.endTime > :startTime
            """)
    List<Show> findOverlappingShowsExcludingCurrent(
            @Param("screenId") Long screenId,
            @Param("showId") Long showId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}