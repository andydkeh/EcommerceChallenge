package com.compass.ecommercechallenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Boolean isActive;

    @CreationTimestamp
    private OffsetDateTime createdAt;
}