package com.compass.ecommercechallenge.dto.cart;

import java.util.UUID;

public record ReadCartItemDTO(UUID idCart, UUID idProduct, UUID idItem, Integer quantity) {
}
