package com.compass.ecommercechallenge.dto.report;

import java.math.BigDecimal;
import java.util.UUID;

public record TopProductSaledDTO(UUID idProduct, String name, Long quantity, String description, BigDecimal price) {
}
