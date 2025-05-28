package com.compass.ecommercechallenge.dto.cart;

import java.util.UUID;

public record CartEditQuantityDTO(UUID idItem, Integer quantity) {
}
