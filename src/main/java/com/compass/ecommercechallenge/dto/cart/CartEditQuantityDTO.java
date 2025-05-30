package com.compass.ecommercechallenge.dto.cart;

import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record CartEditQuantityDTO(UUID idItem,
                                  @PositiveOrZero Integer quantity) {
}
