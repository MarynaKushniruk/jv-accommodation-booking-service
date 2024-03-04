package com.example.jvaccommodationbookingservice.dto.bookingDto;

import com.example.jvaccommodationbookingservice.validation.DateTime;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingUpdateRequestDto {
    @DateTime
    private String checkInDateYearMonthDay;
    @Min(1)
    private Integer daysOfStay;
    private String status;
}
