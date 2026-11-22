package com.dm.darsh_cinespot.service;

import com.dm.darsh_cinespot.dto.request.TheaterRequest;
import com.dm.darsh_cinespot.dto.response.TheaterResponse;
import com.dm.darsh_cinespot.exception.DuplicateResourceException;
import com.dm.darsh_cinespot.exception.ResourceNotFoundException;
import com.dm.darsh_cinespot.model.Theater;
import com.dm.darsh_cinespot.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public TheaterResponse createTheater(TheaterRequest request) {

        if (theaterRepository.existsByNameIgnoreCaseAndCityIgnoreCase(
                request.getName(),
                request.getCity())) {

            throw new DuplicateResourceException(
                    "Theater already exists in this city"
            );
        }

        Theater theater = new Theater();

        theater.setName(request.getName());
        theater.setAddress(request.getAddress());
        theater.setCity(request.getCity());

        return mapToResponse(theaterRepository.save(theater));
    }

    @Transactional(readOnly = true)
    public List<TheaterResponse> getAllTheaters() {

        return theaterRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TheaterResponse getTheaterById(Long id) {

        return mapToResponse(findTheater(id));
    }

    @Transactional(readOnly = true)
    public List<TheaterResponse> findByCity(String city) {

        return theaterRepository
                .findByCityIgnoreCase(city)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TheaterResponse updateTheater(
            Long id,
            TheaterRequest request
    ) {

        Theater theater = findTheater(id);

        theater.setName(request.getName());
        theater.setAddress(request.getAddress());
        theater.setCity(request.getCity());

        return mapToResponse(theaterRepository.save(theater));
    }

    public void deleteTheater(Long id) {

        Theater theater = findTheater(id);

        theaterRepository.delete(theater);
    }

    private Theater findTheater(Long id) {

        return theaterRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theater not found with id: " + id
                        )
                );
    }

    private TheaterResponse mapToResponse(Theater theater) {

        return new TheaterResponse(
                theater.getId(),
                theater.getName(),
                theater.getAddress(),
                theater.getCity()
        );
    }
}