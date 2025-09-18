package com.andydkeh.ecommercechallenge.dto.report;

import com.andydkeh.ecommercechallenge.dto.product.ReadProductDTO;

import java.math.BigDecimal;
import java.util.List;

public record ReportTotalOrdersResponseDTO(Integer totalSales,
                                           BigDecimal totalProfit,
                                           List<ReadProductDTO> topSellingProducts) {
}
