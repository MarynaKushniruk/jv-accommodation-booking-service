package com.example.jvaccommodationbookingservice.repository;

import com.example.jvaccommodationbookingservice.model.Booking;
import com.example.jvaccommodationbookingservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBookingIdAndSessionId(Long bookingId, String sessionId);

    Optional<Payment> findBySessionId(String sessionId);
}
