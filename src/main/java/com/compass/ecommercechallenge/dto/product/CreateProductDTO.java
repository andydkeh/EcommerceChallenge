package com.compass.ecommercechallenge.dto.product;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductDTO (String name,
                                String description,
                                @Positive(message = "The price must be positive") BigDecimal price,
                                @Size(min = 1, message = "the quantity must be greater than 0") Integer quantity) {

}
