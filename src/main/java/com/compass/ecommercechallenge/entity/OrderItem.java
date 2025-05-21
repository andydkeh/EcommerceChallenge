package com.compass.ecommercechallenge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "id_order")
    private UUID orderId;

    @Column(name = "id_product")
    private UUID productId;

    private Integer quantity;

    private Double price;
}