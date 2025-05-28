package com.compass.ecommercechallenge.dto.order;

import com.compass.ecommercechallenge.entity.Order;
import com.compass.ecommercechallenge.entity.Product;

import java.math.BigDecimal;

public record CreateOrderItemDTO(Order orderId,
                                 Product productId,
                                 Integer quantity,
                                 BigDecimal price) {

}
