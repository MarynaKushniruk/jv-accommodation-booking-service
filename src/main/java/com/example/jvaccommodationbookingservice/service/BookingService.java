package com.example.jvaccommodationbookingservice.service;

import com.example.jvaccommodationbookingservice.dto.BookingResponseDto;
import com.example.jvaccommodationbookingservice.dto.CreateBookingRequestDto;
import com.example.jvaccommodationbookingservice.model.BookingStatus;

import java.util.List;

public interface BookingService {
    BookingResponseDto create(CreateBookingRequestDto requestDto);
    List<BookingResponseDto> findByUserIdAndBookingStatus(Long id, BookingStatus status);
    List<BookingResponseDto> findAllByUserId(String email);
    BookingResponseDto getBookingById(Long id);
    BookingResponseDto updateBookingById(Long id, CreateBookingRequestDto requestDto);
    void deleteBookingById(Long id);
}
