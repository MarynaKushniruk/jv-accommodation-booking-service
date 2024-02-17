package com.example.jvaccommodationbookingservice.mapper;

import com.example.jvaccommodationbookingservice.config.MapperConfig;
import com.example.jvaccommodationbookingservice.dto.AccommodationResponseDto;
import com.example.jvaccommodationbookingservice.dto.AddAccommodationRequestDto;
import com.example.jvaccommodationbookingservice.model.Accommodation;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = MapperConfig.class)
public interface AccommodationMapper {
    Accommodation toModel(AddAccommodationRequestDto requestDto);
    AccommodationResponseDto toDto(Accommodation accommodation);
}
