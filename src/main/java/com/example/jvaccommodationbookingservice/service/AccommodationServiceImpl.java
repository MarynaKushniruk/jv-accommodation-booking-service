package com.example.jvaccommodationbookingservice.service;

import com.example.jvaccommodationbookingservice.dto.AccommodationResponseDto;
import com.example.jvaccommodationbookingservice.dto.AddAccommodationRequestDto;
import com.example.jvaccommodationbookingservice.exception.EntityNotFoundException;
import com.example.jvaccommodationbookingservice.mapper.AccommodationMapper;
import com.example.jvaccommodationbookingservice.model.Accommodation;
import com.example.jvaccommodationbookingservice.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;
    @Override
    public AccommodationResponseDto add(AddAccommodationRequestDto accommodation) {
        Accommodation accommodationModel = accommodationRepository.save(accommodationMapper.toModel(accommodation));
        return accommodationMapper.toDto(accommodationModel);
    }

    @Override
    public List<AccommodationResponseDto> findAll(Pageable pageable) {
        return accommodationRepository
                .findAll()
                .stream()
                .map(accommodationMapper::toDto)
                .toList();
    }

    @Override
    public AccommodationResponseDto getAccommodationById(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findAccommodationById(accommodationId).orElseThrow(()->
                new EntityNotFoundException("Can't find accommodation by id " + accommodationId ));
        return accommodationMapper.toDto(accommodation);
    }

    @Override
    @Transactional
    public AccommodationResponseDto update(AddAccommodationRequestDto requestDto, Long id) {
        Accommodation accommodation = accommodationRepository.findAccommodationById(id).orElseThrow(()->
                new EntityNotFoundException("Can't find accommodation by id " + id ));
        accommodation.setAddress(requestDto.getAddress());
        accommodation.setAmenities(requestDto.getAmenities());
        accommodation.setSize(requestDto.getSize());
        accommodation.setType(requestDto.getType());
        accommodation.setDailyRate(requestDto.getDailyRate());
        accommodation.setNumberOfAvailable(requestDto.getNumberOfAvailable());
        return accommodationMapper.toDto(accommodationRepository.save(accommodation));
    }
    @Override
    public void deleteById(Long id) {
        accommodationRepository.deleteById(id);
    }
}
