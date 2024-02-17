package com.example.jvaccommodationbookingservice.repository;

import com.example.jvaccommodationbookingservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(Role.RoleName roleName);
}
