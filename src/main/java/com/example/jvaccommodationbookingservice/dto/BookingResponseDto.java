package com.example.jvaccommodationbookingservice.dto;



import com.example.jvaccommodationbookingservice.model.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class BookingResponseDto {
    private Long accommodationId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
