package com.example.jvaccommodationbookingservice.controller;

import com.example.jvaccommodationbookingservice.dto.BookingResponseDto;
import com.example.jvaccommodationbookingservice.dto.CreateBookingRequestDto;
import com.example.jvaccommodationbookingservice.model.BookingStatus;
import com.example.jvaccommodationbookingservice.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create a new booking", description = "Create and save a new booking")
    @ResponseStatus(HttpStatus.CREATED)
    BookingResponseDto create(@RequestBody CreateBookingRequestDto requestDto) {
        return bookingService.create(requestDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority({'ADMIN'})")
    @Operation(summary = "Get bookings by user_Id and status", description = "Get bookings by user_Id and status")
    List<BookingResponseDto> searchByIdAndStatus(@PathVariable("user_id") Long id,
                                                 @PathVariable("status") BookingStatus status) {
        return bookingService.findByUserIdAndBookingStatus(id, status);
    }
    @GetMapping("/my")
    @Operation(summary = "Get all bookings of authenticated user", description = "Get all bookings of user")
    List<BookingResponseDto> findAll(Authentication authentication) {
        return bookingService.findAllByUserId(authentication.getName());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by booking id", description = "Get information about booking by id")
    BookingResponseDto findBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update booking by booking id", description = "Update information about booking by id")
    BookingResponseDto update(@PathVariable Long id, @RequestBody CreateBookingRequestDto requestDto ) {
        return bookingService.updateBookingById(id, requestDto );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete booking by booking id", description = "Delete booking by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBookingById(@PathVariable Long id) {
        bookingService.deleteBookingById(id);
    }

}
