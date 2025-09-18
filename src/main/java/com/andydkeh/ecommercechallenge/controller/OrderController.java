package com.andydkeh.ecommercechallenge.controller;

import com.andydkeh.ecommercechallenge.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @Transactional
    @PostMapping("/finishOrder")
    public ResponseEntity<Void> finishOrder(@AuthenticationPrincipal Jwt jwt){
        orderService.finishOrder(UUID.fromString(jwt.getClaim("sub")));
        return ResponseEntity.ok().build();
    }
}