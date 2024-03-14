package com.example.jvaccommodationbookingservice.service.booking;

import com.example.jvaccommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.jvaccommodationbookingservice.dto.booking.BookingResponseDto;
import com.example.jvaccommodationbookingservice.dto.booking.BookingUpdateRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    BookingResponseDto create(BookingRequestDto requestDto);
    List<BookingResponseDto> findByUserIdAndBookingStatus(Long id, String status, Pageable pageable);
    List<BookingResponseDto> findAllMyBookings(final Pageable pageable);;
    BookingResponseDto getById(Long id);
    void updateBookingById(Long id, BookingUpdateRequestDto requestDto);
    void deleteBookingById(Long id);
}
