package com.example.jvaccommodationbookingservice.mapper;

import com.example.jvaccommodationbookingservice.config.MapperConfig;
import com.example.jvaccommodationbookingservice.dto.BookingResponseDto;
import com.example.jvaccommodationbookingservice.dto.CreateBookingRequestDto;
import com.example.jvaccommodationbookingservice.model.Booking;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookingMapper {
    Booking toModel(CreateBookingRequestDto requestDto);
    BookingResponseDto toDto(Booking booking);
}
