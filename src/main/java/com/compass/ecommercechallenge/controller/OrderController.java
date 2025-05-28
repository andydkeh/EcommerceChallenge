package com.compass.ecommercechallenge.controller;

import com.compass.ecommercechallenge.service.OrderService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

//    @PostMapping("/finishOrder")
//    public RequestEntity<Void> finishOrder(@AuthenticationPrincipal Jwt jwt){
//        return ResponseEntity.ok().build();
//    }
}
