package com.dm.darsh_cinespot.repository;

import com.dm.darsh_cinespot.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

    List<Screen> findByTheaterId(Long theaterId);

    boolean existsByTheaterIdAndScreenName(
            Long theaterId,
            String screenName
    );
}