package com.example.jvaccommodationbookingservice.service.bookingservice;

import com.example.jvaccommodationbookingservice.dto.bookingDto.BookingRequestDto;
import com.example.jvaccommodationbookingservice.dto.bookingDto.BookingResponseDto;
import com.example.jvaccommodationbookingservice.dto.bookingDto.BookingUpdateRequestDto;
import com.example.jvaccommodationbookingservice.exception.DataProcessingException;
import com.example.jvaccommodationbookingservice.exception.EntityNotFoundException;
import com.example.jvaccommodationbookingservice.mapper.BookingMapper;
import com.example.jvaccommodationbookingservice.model.Accommodation;
import com.example.jvaccommodationbookingservice.model.Booking;
import com.example.jvaccommodationbookingservice.model.User;
import com.example.jvaccommodationbookingservice.repository.BookingRepository;
import com.example.jvaccommodationbookingservice.service.accommodationservice.AccommodationService;
import com.example.jvaccommodationbookingservice.service.userservice.UserService;
import com.example.jvaccommodationbookingservice.telegram.BookingBot;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private static final DateTimeFormatter PATTERN_OF_DATE =
            DateTimeFormatter.ofPattern("yyyy, MM, dd");

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final AccommodationService accommodationService;
    private final BookingBot bookingBot;
    @Override
    public BookingResponseDto create(BookingRequestDto requestDto) {
        checkBookingWithStatusPending();
        LocalDateTime checkInDate = checkAndParseCheckInDateToLocalDateTime(
                requestDto.getCheckInDateYearMonthDay()
        );
        LocalDateTime checkOutDate = checkInDate.plusDays(requestDto.getDaysOfStay()).minusSeconds(1);
        Accommodation accommodation = checkAndGetAccommodation(requestDto.getAccommodationId());
        checkingDateBookingAndAvailability(checkInDate, checkOutDate, accommodation);
        Booking booking = bookingMapper.toEntity(requestDto, checkInDate,checkOutDate);
        booking.setUser(userService.getAuthenticated());
        booking.setAccommodation(accommodation);
        Booking savedBooking = bookingRepository.save(booking);
        bookingBot.handleIncomingMessage("Create new booking |" + System.lineSeparator()
                + bookingMapper.toDto(savedBooking).toString());
        return bookingMapper.toDto(savedBooking);
    }

    @Override
    public List<BookingResponseDto> findByUserIdAndBookingStatus(Long userId,
                                                                 String status,
                                                                 Pageable pageable) {
        if (!userService.existsById(userId)) {
            throw new EntityNotFoundException("Can't find user by id: " + userId);
        }

        Booking.Status validStatus = checkValidStatus(status);
        return bookingRepository.findAllByUserIdAndStatus(userId,validStatus, pageable)
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public List<BookingResponseDto> findAllMyBookings(final Pageable pageable) {
        User user = userService.getAuthenticated();
        return bookingRepository.findAllByUser(user, pageable)
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public BookingResponseDto getById(Long id) {
        return bookingMapper.toDto(getBookingById(id));
    }

    @Override
    public void updateBookingById(Long id, BookingUpdateRequestDto request) {
        Booking booking = getBookingById(id);
        checkCorrectUserForBooking(booking);

        if (request.getCheckInDateYearMonthDay() != null
                && !request.getCheckInDateYearMonthDay().isEmpty()
                && request.getDaysOfStay() != null) {
            LocalDateTime checkInDate =
                    checkAndParseCheckInDateToLocalDateTime(request.getCheckInDateYearMonthDay());
            LocalDateTime checkOutDate =
                    checkInDate.plusDays(request.getDaysOfStay()).minusSeconds(1);
            checkingDateBookingAndAvailability(
                    checkInDate, checkOutDate, booking.getAccommodation()
            );

            if (!booking.getCheckInDate().equals(checkInDate)) {
                booking.setCheckInDate(checkInDate);
                booking.setCheckOutDate(checkOutDate);
            }
        }

        if (request.getStatus() != null) {
            Booking.Status status = checkValidStatus(request.getStatus());

            if (!booking.getStatus().equals(status)) {
                booking.setStatus(status);
            }

        }

        bookingBot.handleIncomingMessage("Update booking status |" + System.lineSeparator()
                + bookingMapper.toDto(booking).toString());
        bookingRepository.save(booking);
    }

    @Override
    public void deleteBookingById(Long id) {
        Booking booking = getBookingById(id);
        checkCorrectUserForBooking(booking);
        bookingRepository.deleteById(id);
    }
    private void checkBookingWithStatusPending() {
        Pageable pageable = PageRequest.of(0, 1);
        User user = userService.getAuthenticated();
        final List<BookingResponseDto> allByUserIdAndStatus
                = findByUserIdAndBookingStatus(user.getId(), Booking.Status.PENDING.name(), pageable);

        if (!allByUserIdAndStatus.isEmpty()) {
            throw new DataProcessingException("It is not possible to create a new booking until "
                    + "you have paid or canceled the previous booking.");
        }
    }

    private Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find booking by id: " + id)
        );
    }

    private LocalDateTime checkAndParseCheckInDateToLocalDateTime(String date) {
        LocalDate localDate = LocalDate.parse(date, PATTERN_OF_DATE);

        if (localDate.isBefore(LocalDate.now())) {
            throw new DataProcessingException("The date is incorrect: " + localDate
                    + " , please enter a date greater than the current one: "
                    + LocalDateTime.now());
        }

        return localDate.atTime(12, 0, 0);
    }

    private Accommodation checkAndGetAccommodation(Long accommodationId) {
        Accommodation accommodation =
                accommodationService.getAccommodationById(accommodationId);

        if (accommodation.getAvailability() == 0) {
            throw new DataProcessingException(
                    "Unable to booking this property, availability is: 0");
        }

        return accommodation;
    }

    private void checkingDateBookingAndAvailability(
            LocalDateTime checkInDate, LocalDateTime checkOutDate, Accommodation accommodation
    ) {
        List<Booking> allByCheckInDateAndCheckOutDate =
                bookingRepository.findAllBetweenCheckInDateAndCheckOutDate(
                        checkInDate, checkOutDate, accommodation.getId()
                );
        final List<Booking> bookings =
                checkStatus(allByCheckInDateAndCheckOutDate);

        boolean checkAvailability = accommodation.getAvailability()
                - bookings.size() <= 0;

        if (!bookings.isEmpty() && checkAvailability) {
            throw new DataProcessingException("There are no vacancies in the interval from: "
                    + checkInDate + ", to: " + checkOutDate);
        }
    }
    private Booking.Status checkValidStatus(String statusName) {
        Booking.Status validStatus = null;

        for (Booking.Status status : Booking.Status.values()) {
            if (statusName.toUpperCase().trim().equals(status.name())) {
                validStatus = status;
            }
        }

        if (validStatus == null) {
            throw new DataProcessingException(
                    "This status does not exist or the data is not entered correctly"
            );
        }

        return validStatus;
    }
    private void checkCorrectUserForBooking(Booking booking) {
        User user = userService.getAuthenticated();

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new DataProcessingException("Unable to process booking by id: "
                    + booking.getId() + ", it is not yours");
        }
    }
    private List<Booking> checkStatus(List<Booking> bookings) {
        List<Booking> bookingWithPendingStatus = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking != null && booking.getStatus().equals(Booking.Status.PENDING)
                    || booking.getStatus().equals(Booking.Status.CONFIRMED)) {
                bookingWithPendingStatus.add(booking);
            }
        }
        return bookingWithPendingStatus;
    }
}
