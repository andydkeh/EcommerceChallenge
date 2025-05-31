package com.compass.ecommercechallenge.dto.report;

import java.math.BigDecimal;
import java.util.List;

public record TotalSalesDTO(
        Integer totalSalesCount,
        BigDecimal totalProfit,
        List<TopProductSaleDatesDTO> topProducts
) {}