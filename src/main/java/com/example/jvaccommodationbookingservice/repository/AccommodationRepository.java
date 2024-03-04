package com.example.jvaccommodationbookingservice.repository;

import com.example.jvaccommodationbookingservice.model.Accommodation;
import com.example.jvaccommodationbookingservice.model.Address;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Optional<Accommodation> findByTypeAndAddressIdAndSizeAndDailyRate(
            Accommodation.Type type,
            Long addressId,
            Accommodation.Size gsize,
            BigDecimal dailyRate);
    @Nonnull
    @EntityGraph(attributePaths = {"address", "amenities"})
    Page<Accommodation> findAll(@Nonnull Pageable pageable);

    @Nonnull
    @EntityGraph(attributePaths = {"address", "amenities"})
    Optional<Accommodation> findById(@Nonnull Long id);

    @EntityGraph(attributePaths = {"address", "amenities"})
    List<Accommodation> findByAddress(Address address);
}
