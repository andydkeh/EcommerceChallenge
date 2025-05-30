package com.compass.ecommercechallenge.controller;

import com.compass.ecommercechallenge.dto.report.LowStockDTO;
import com.compass.ecommercechallenge.dto.report.TopCustomerDTO;
import com.compass.ecommercechallenge.dto.report.TopProductSaledDTO;
import com.compass.ecommercechallenge.service.OrderService;
import com.compass.ecommercechallenge.service.ReportService;
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
    public ResponseEntity<Integer> totalSales(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDate,
                                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDate
    ) {
        reportService.findTotalSales(startDate, endDate);
        return ResponseEntity.ok().build();
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
