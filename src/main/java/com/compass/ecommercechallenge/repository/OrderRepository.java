package com.compass.ecommercechallenge.repository;

import com.compass.ecommercechallenge.dto.report.TopCustomerDTO;
import com.compass.ecommercechallenge.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Integer countOrdersByCreatedAtBetween(OffsetDateTime createdAtAfter, OffsetDateTime createdAtBefore);

    @Query("""
            SELECT new com.compass.ecommercechallenge.dto.report.TopCustomerDTO (
            ord.user.id,
            COUNT(ord)
            )
            FROM Order ord 
            GROUP BY ord.user.id
            ORDER BY COUNT(ord) DESC
"""
        )
    List<TopCustomerDTO> findTopCustomers();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    Long countOrdersByPeriod(@Param("startDate") OffsetDateTime startDate, @Param("endDate") OffsetDateTime endDate);

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalByPeriod(@Param("startDate") OffsetDateTime startDate, @Param("endDate") OffsetDateTime endDate);
}
