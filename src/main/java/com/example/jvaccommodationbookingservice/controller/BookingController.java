package com.example.jvaccommodationbookingservice.controller;

import com.example.jvaccommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.jvaccommodationbookingservice.dto.booking.BookingResponseDto;
import com.example.jvaccommodationbookingservice.dto.booking.BookingUpdateRequestDto;
import com.example.jvaccommodationbookingservice.service.booking.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bookings management", description = "Endpoints for bookings action")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create a new booking", description = "Create and save a new booking")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    BookingResponseDto create(@RequestBody BookingRequestDto requestDto) {
        return bookingService.create(requestDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Get booking", description = "Get booking by user id and status")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponseDto> getAllByUserIdAndStatus(
            @RequestParam(name = "user_id", required = true) Long userId,
            @RequestParam(name = "status", required = false) String status,
            Pageable pageable
    ) {
        return bookingService.findByUserIdAndBookingStatus(userId, status, pageable);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "Get all my bookings", description = "Get all user bookings")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponseDto> getAllMyBookings(Pageable pageable) {
        return bookingService.findAllMyBookings(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "Get booking by id", description = "Get booking by id")
    @ResponseStatus(HttpStatus.OK)
    public BookingResponseDto getById(@PathVariable Long id) {
        return bookingService.getById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "Update by id", description = "Update by id")
    @ResponseStatus(HttpStatus.OK)
    public void updateById(@PathVariable Long id, @RequestBody BookingUpdateRequestDto request) {
        bookingService.updateBookingById(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "Delete booking by booking id", description = "Delete booking by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBookingById(@PathVariable Long id) {
        bookingService.deleteBookingById(id);
    }

}
