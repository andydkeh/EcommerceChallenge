package com.compass.ecommercechallenge.dto.report;

import java.util.UUID;

public record LowStockDTO(
        UUID id,
        String name,
        Integer quantity
) {}
