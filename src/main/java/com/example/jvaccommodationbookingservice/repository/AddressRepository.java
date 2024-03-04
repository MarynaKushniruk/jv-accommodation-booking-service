package com.example.jvaccommodationbookingservice.repository;

import com.example.jvaccommodationbookingservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByAddress(String address);

    Optional<Address> findByAddress(String address);

}
