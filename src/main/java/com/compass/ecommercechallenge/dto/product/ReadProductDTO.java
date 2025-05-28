package com.compass.ecommercechallenge.dto.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ReadProductDTO(UUID id, String name, String description, String price) {

}
