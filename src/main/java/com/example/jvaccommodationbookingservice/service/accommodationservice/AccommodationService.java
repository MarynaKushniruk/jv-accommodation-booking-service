package com.example.jvaccommodationbookingservice.service.accommodationservice;

import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationFullInfoResponseDto;
import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationIncompleteInfoResponseDto;
import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationRequestDto;
import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationUpdateRequestDto;
import com.example.jvaccommodationbookingservice.model.Accommodation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccommodationService {
    AccommodationFullInfoResponseDto add(AccommodationRequestDto accommodation);
    List<AccommodationIncompleteInfoResponseDto> findAll(Pageable pageable);
    AccommodationFullInfoResponseDto getById(Long accommodationId);
    void update(AccommodationUpdateRequestDto requestDto, Long id);
    void deleteById(Long id);
    Accommodation getAccommodationById(final Long id);
}
