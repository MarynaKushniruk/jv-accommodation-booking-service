package com.example.jvaccommodationbookingservice.mapper;

import com.example.jvaccommodationbookingservice.config.MapperConfig;
import com.example.jvaccommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.jvaccommodationbookingservice.dto.booking.BookingResponseDto;
import com.example.jvaccommodationbookingservice.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(config = MapperConfig.class)
public interface BookingMapper {
    @Mapping(target = "accommodationId", source = "booking.accommodation.id")
    @Mapping(target = "userId", source = "booking.user.id")
    BookingResponseDto toDto(Booking booking);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "accommodation", ignore = true)
    @Mapping(target = "checkInDate", source = "checkInDate")
    @Mapping(target = "checkOutDate", source = "checkOutDate")
    @Mapping(target = "status", expression = "java(Booking.Status.PENDING)")
    Booking toEntity(
            BookingRequestDto request,
            LocalDateTime checkInDate,
            LocalDateTime checkOutDate
    );
}
