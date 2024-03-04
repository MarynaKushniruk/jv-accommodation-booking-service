package com.example.jvaccommodationbookingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE addresses SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @com.example.jvaccommodationbookingservice.validation.Address
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
}
