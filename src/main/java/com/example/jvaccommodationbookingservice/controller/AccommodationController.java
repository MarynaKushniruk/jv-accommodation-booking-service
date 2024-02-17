package com.example.jvaccommodationbookingservice.controller;

import com.example.jvaccommodationbookingservice.dto.AddAccommodationRequestDto;
import com.example.jvaccommodationbookingservice.dto.AccommodationResponseDto;
import com.example.jvaccommodationbookingservice.service.AccommodationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accommodations")
public class AccommodationController {

private final AccommodationService accommodationService;

    @PostMapping
    @PreAuthorize("hasAuthority({'ADMIN'})")
    @Operation(summary = "Create a new accommodation", description = "Create and save a new accommodation")
    @ResponseStatus(HttpStatus.CREATED)
    AccommodationResponseDto add(@RequestBody AddAccommodationRequestDto accommodation) {
        return accommodationService.add(accommodation);
    }

    @GetMapping
    @Operation(summary = "Get all accommodations", description = "Get a list of all available accommodations")
    List<AccommodationResponseDto> getAll(Pageable pageable) {
        return accommodationService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get accommodation by id", description = "Get accommodation by id")
    AccommodationResponseDto getAccommodationById(@PathVariable Long id) {
        return accommodationService.getAccommodationById(id);
    }

    @PreAuthorize("hasAuthority({'ADMIN'})")
    @PutMapping("/{id}")
    @Operation(summary = "Update an accommodation", description = "Update an accommodation by id")
    AccommodationResponseDto update(@RequestBody AddAccommodationRequestDto requestDto,
                                    @PathVariable Long id) {
        return accommodationService.update(requestDto, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority({'ADMIN'})")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an accommodation", description = "Delete an accommodation by id")
    void deleteAccommodationById(@PathVariable Long id) {
        accommodationService.deleteById(id);
    }
}
