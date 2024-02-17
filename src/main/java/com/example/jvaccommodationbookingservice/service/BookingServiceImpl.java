package com.example.jvaccommodationbookingservice.service;

import com.example.jvaccommodationbookingservice.dto.BookingResponseDto;
import com.example.jvaccommodationbookingservice.dto.CreateBookingRequestDto;
import com.example.jvaccommodationbookingservice.exception.EntityNotFoundException;
import com.example.jvaccommodationbookingservice.mapper.BookingMapper;
import com.example.jvaccommodationbookingservice.model.Booking;
import com.example.jvaccommodationbookingservice.model.BookingStatus;
import com.example.jvaccommodationbookingservice.model.User;
import com.example.jvaccommodationbookingservice.repository.AccommodationRepository;
import com.example.jvaccommodationbookingservice.repository.BookingRepository;
import com.example.jvaccommodationbookingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    @Override
    public BookingResponseDto create(CreateBookingRequestDto requestDto) {
        Booking booking = bookingMapper.toModel(requestDto);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public List<BookingResponseDto> findByUserIdAndBookingStatus(Long id, BookingStatus status) {
        return bookingRepository.findAllByIdAndStatus(id,status)
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public List<BookingResponseDto> findAllByUserId(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new EntityNotFoundException("Can't find user by email " + email));
        return bookingRepository.findAllByUserId(user.getId())
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Can't find " +
                "booking by id " + id));
        return bookingMapper.toDto(booking);
    }

    @Override
    public BookingResponseDto updateBookingById(Long id, CreateBookingRequestDto requestDto) {
        Booking booking = bookingRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Can't find " +
                "booking by id " + id));
        booking.setAccommodation(accommodationRepository.findAccommodationById(requestDto.getAccommodationId())
                .orElseThrow(()-> new EntityNotFoundException("Can't find " +
                        "accommodation by id " + id)));
        booking.setCheckInTime(requestDto.getCheckInTime());
        booking.setCheckOutTime(requestDto.getCheckOutTime());
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }
}
