package com.example.jvaccommodationbookingservice.service;

import com.example.jvaccommodationbookingservice.dto.AccommodationResponseDto;
import com.example.jvaccommodationbookingservice.dto.AddAccommodationRequestDto;
import com.example.jvaccommodationbookingservice.model.Accommodation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccommodationService {
    AccommodationResponseDto add(AddAccommodationRequestDto accommodation);
    List<AccommodationResponseDto> findAll(Pageable pageable);
    AccommodationResponseDto getAccommodationById(Long accommodationId);
    AccommodationResponseDto update(AddAccommodationRequestDto requestDto, Long id);
    void deleteById(Long id);
}
