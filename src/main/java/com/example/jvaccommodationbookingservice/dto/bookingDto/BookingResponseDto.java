package com.example.jvaccommodationbookingservice.dto.bookingDto;

import java.time.LocalDateTime;

public record BookingResponseDto(
        Long id,
        LocalDateTime checkInDate,
        LocalDateTime checkOutDate,
        Long accommodationId,
        Long userId,
        String status
) {
}
