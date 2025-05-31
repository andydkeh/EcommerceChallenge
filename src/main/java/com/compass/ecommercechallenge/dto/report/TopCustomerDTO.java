package com.compass.ecommercechallenge.dto.report;

import java.math.BigDecimal;
import java.util.UUID;

public record TopCustomerDTO(
        UUID customerId,
        Long totalOrders
) { }
