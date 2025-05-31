package com.compass.ecommercechallenge.repository;

import com.compass.ecommercechallenge.dto.report.TopProductSaleDatesDTO;
import com.compass.ecommercechallenge.dto.report.TopProductSaledDTO;
import com.compass.ecommercechallenge.entity.Order;
import com.compass.ecommercechallenge.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
       @Query("""
            SELECT new com.compass.ecommercechallenge.dto.report.TopProductSaledDTO (
                oi.productId.id,
                p.name,
                SUM(oi.quantity),
                p.description,
                p.price
            )
            FROM OrderItem oi
            JOIN oi.productId p
            GROUP BY oi.productId, p.name
            ORDER BY SUM(oi.quantity) DESC
"""
    )
    List<TopProductSaledDTO> findTopProductSaled();

    @Query("""
            SELECT new com.compass.ecommercechallenge.dto.report.TopProductSaleDatesDTO (
                oi.productId.id,
                p.name,
                SUM(oi.quantity)
            )
            FROM OrderItem oi
            JOIN oi.productId p
            WHERE oi.createdAt BETWEEN :startDate AND :endDate
            GROUP BY oi.productId, p.name
            ORDER BY SUM(oi.quantity) DESC
"""
    )
    List<TopProductSaleDatesDTO> findTopProductSaledByCreatedAtBetween(@Param("startDate") OffsetDateTime startDate, @Param("endDate") OffsetDateTime endDate);
}
