package com.dm.darsh_cinespot.repository;

import com.dm.darsh_cinespot.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    List<Theater> findByCityIgnoreCase(String city);

    List<Theater> findByNameContainingIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndCityIgnoreCase(
            String name,
            String city
    );
}