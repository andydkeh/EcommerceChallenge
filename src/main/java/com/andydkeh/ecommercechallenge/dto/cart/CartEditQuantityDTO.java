package com.andydkeh.ecommercechallenge.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record CartEditQuantityDTO(@NotNull UUID idItem,
                                  @PositiveOrZero Integer quantity) {
}
