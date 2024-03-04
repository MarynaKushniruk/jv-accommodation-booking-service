package com.example.jvaccommodationbookingservice.repository;

import com.example.jvaccommodationbookingservice.model.Booking;
import com.example.jvaccommodationbookingservice.model.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b "
            + "FROM Booking b "
            + "WHERE (b.checkInDate BETWEEN :checkInDate AND :checkOutDate "
            + "OR b.checkOutDate BETWEEN :checkInDate AND :checkOutDate) "
            + "AND b.accommodation.id = :accommodationId")
    @EntityGraph(attributePaths = {"accommodation", "user"})
    List<Booking> findAllBetweenCheckInDateAndCheckOutDate(
            LocalDateTime checkInDate, LocalDateTime checkOutDate, Long accommodationId
    );

    @EntityGraph(attributePaths = {"accommodation", "user"})
    Page<Booking> findAllByUserIdAndStatus(Long id, Booking.Status status, Pageable pageable);

    @EntityGraph(attributePaths = {"accommodation", "user"})
    Page<Booking> findAllByUser(User user, Pageable pageable);
    @Nonnull
    @EntityGraph(attributePaths = {"accommodation", "user"})
    Optional<Booking> findById(@Nonnull Long id);
}
