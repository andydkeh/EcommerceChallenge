package com.andydkeh.ecommercechallenge.controller;

import com.andydkeh.ecommercechallenge.dto.report.LowStockDTO;
import com.andydkeh.ecommercechallenge.dto.report.TopCustomerDTO;
import com.andydkeh.ecommercechallenge.dto.report.TopProductSaledDTO;
import com.andydkeh.ecommercechallenge.dto.report.TotalSalesDTO;
import com.andydkeh.ecommercechallenge.service.OrderService;
import com.andydkeh.ecommercechallenge.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
public class ReportController {

    private final ReportService reportService;
    private final OrderService orderService;

    public ReportController(ReportService reportService, OrderService orderService) {
        this.reportService = reportService;
        this.orderService = orderService;
    }

    @GetMapping("/totalSales")
    public ResponseEntity<TotalSalesDTO> totalSales(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDate
    ) {
        TotalSalesDTO result = reportService.findTotalSales(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/top-customers")
    public ResponseEntity<List<TopCustomerDTO>> findTopCustomers() {
        List<TopCustomerDTO> result = reportService.findTopCustomers();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/top-product")
    public ResponseEntity<List<TopProductSaledDTO>> findTopProductsSaled(){
        List<TopProductSaledDTO> result  = reportService.findTopProducts();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<LowStockDTO>> findLowStockProducts(
            @RequestParam(defaultValue = "5") Integer threshold) {
        List<LowStockDTO> result = reportService.findLowStock(threshold);
        return ResponseEntity.ok(result);
    }
}
