package com.andydkeh.ecommercechallenge.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CartItemDTO (@NotNull UUID idProduct,
                           @Min(value = 1, message = "the quantity must be greater than 0") Integer quantity){
}
