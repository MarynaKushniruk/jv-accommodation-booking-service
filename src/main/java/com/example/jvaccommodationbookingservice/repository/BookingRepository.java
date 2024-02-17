package com.example.jvaccommodationbookingservice.repository;

import com.example.jvaccommodationbookingservice.model.Booking;
import com.example.jvaccommodationbookingservice.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByIdAndStatus(Long id, BookingStatus status);
    List<Booking> findAllByUserId(Long id);
}
