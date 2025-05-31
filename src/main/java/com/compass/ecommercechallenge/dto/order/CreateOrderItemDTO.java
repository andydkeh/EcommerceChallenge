package com.compass.ecommercechallenge.dto.order;

import com.compass.ecommercechallenge.entity.Order;
import com.compass.ecommercechallenge.entity.Product;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateOrderItemDTO(Order orderId,
                                 Product productId,
                                 @Positive Integer quantity,
                                 @Positive BigDecimal price) {

}
