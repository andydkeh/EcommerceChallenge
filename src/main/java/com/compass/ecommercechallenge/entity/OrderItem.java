package com.compass.ecommercechallenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private Integer quantity;
    private Double price;
}