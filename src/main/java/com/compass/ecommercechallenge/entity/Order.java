package com.compass.ecommercechallenge.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID userId;
    private Double totalPrice;
    private Boolean status;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}