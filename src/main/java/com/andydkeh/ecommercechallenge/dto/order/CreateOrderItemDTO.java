package com.andydkeh.ecommercechallenge.dto.order;

import com.andydkeh.ecommercechallenge.entity.Order;
import com.andydkeh.ecommercechallenge.entity.Product;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateOrderItemDTO(Order orderId,
                                 Product productId,
                                 @Positive Integer quantity,
                                 @Positive BigDecimal price) {

}
