package com.example.jvaccommodationbookingservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class CreateBookingRequestDto {
    @NotNull
    private Long accommodationId;
    @NotNull
    private LocalDateTime checkInTime;
    @NotNull
    private LocalDateTime checkOutTime;
}
