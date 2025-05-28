package com.compass.ecommercechallenge.service;

import com.compass.ecommercechallenge.entity.Order;
import com.compass.ecommercechallenge.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

//    public void createOrder(UUID idUSer,) {
//
//        orderRepository.save(order);
//    }
}
