package com.example.jvaccommodationbookingservice.repository;

import com.example.jvaccommodationbookingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
