package com.example.jvaccommodationbookingservice.repository;

import com.example.jvaccommodationbookingservice.model.Accommodation;
import com.example.jvaccommodationbookingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Optional<Accommodation> findAccommodationById(Long id);
}
