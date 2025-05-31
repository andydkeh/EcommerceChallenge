package com.compass.ecommercechallenge.service;

import com.compass.ecommercechallenge.dto.report.*;
import com.compass.ecommercechallenge.entity.Product;
import com.compass.ecommercechallenge.repository.OrderItemRepository;
import com.compass.ecommercechallenge.repository.OrderRepository;
import com.compass.ecommercechallenge.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ReportService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public ReportService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    public List<TopCustomerDTO> findTopCustomers(){
        return orderRepository.findTopCustomers();
    }

    public List<TopProductSaledDTO> findTopProducts(){
        return orderItemRepository.findTopProductSaled();
    }

    public List<LowStockDTO> findLowStock(Integer threshold){
        List<Product> products = productRepository.findByQuantityLessThan(threshold);
        return products.stream()
                .map(p -> new LowStockDTO(
                        p.getId(),
                        p.getName(),
                        p.getQuantity())
                )
                .toList();
    }

    public TotalSalesDTO findTotalSales(OffsetDateTime startDate, OffsetDateTime endDate){
        Long count = orderRepository.countOrdersByPeriod(startDate, endDate);
        BigDecimal total = orderRepository.sumTotalByPeriod(startDate, endDate);

        return new TotalSalesDTO(
                count != null ? count.intValue() : 0,
                total != null ? total : BigDecimal.ZERO,
                orderItemRepository.findTopProductSaledByCreatedAtBetween(startDate, endDate)
        );
    }
}
