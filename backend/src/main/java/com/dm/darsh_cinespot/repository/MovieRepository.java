package com.dm.darsh_cinespot.repository;

import com.dm.darsh_cinespot.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByGenreIgnoreCase(String genre);

    List<Movie> findByLanguageIgnoreCase(String language);

    boolean existsByTitleIgnoreCase(String title);
}