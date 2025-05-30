package com.compass.ecommercechallenge.dto.report;

import java.util.UUID;

public record TopProductSaleDatesDTO(
        UUID productId,
        String productName,
        Long quantitySold
) {}
