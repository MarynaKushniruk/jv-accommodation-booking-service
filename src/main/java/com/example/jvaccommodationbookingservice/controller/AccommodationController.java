package com.example.jvaccommodationbookingservice.controller;

import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationFullInfoResponseDto;
import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationIncompleteInfoResponseDto;
import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationRequestDto;
import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationUpdateRequestDto;
import com.example.jvaccommodationbookingservice.service.accommodationservice.AccommodationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Accommodation management", description = "Endpoints for accommodations action")
@RequiredArgsConstructor
@RestController
@RequestMapping("/accommodations")
public class AccommodationController {

private final AccommodationService accommodationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Add accommodation", description = "Added accommodation")
    @ResponseStatus(HttpStatus.CREATED)
    public AccommodationFullInfoResponseDto add(@RequestBody @Valid AccommodationRequestDto accommodation) {
        return accommodationService.add(accommodation);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all accommodations", description = "Get a list of all available accommodations")
    public List<AccommodationIncompleteInfoResponseDto> getAll(Pageable pageable) {
        return accommodationService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "Get accommodation by id", description = "Get accommodation by id")
    @ResponseStatus(HttpStatus.OK)
    public AccommodationFullInfoResponseDto getAccommodationById(@PathVariable Long id) {
        return accommodationService.getById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Update by id", description = "Update accommodation by id")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody AccommodationUpdateRequestDto requestDto,
                                    @PathVariable Long id) {
        accommodationService.update(requestDto, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an accommodation", description = "Delete an accommodation by id")
    void deleteAccommodationById(@PathVariable Long id) {
        accommodationService.deleteById(id);
    }
}
