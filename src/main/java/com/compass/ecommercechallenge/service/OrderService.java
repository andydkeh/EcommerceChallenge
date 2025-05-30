package com.compass.ecommercechallenge.service;

import com.compass.ecommercechallenge.entity.CartItem;
import com.compass.ecommercechallenge.entity.Order;
import com.compass.ecommercechallenge.entity.OrderItem;
import com.compass.ecommercechallenge.entity.User;
import com.compass.ecommercechallenge.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    public final ProductRepository productRepository;
    public final ProductService productService;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository,
                        OrderItemRepository orderItemRepository,
                        ProductRepository productRepository,
                        ProductService productService, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Order createOrder(User user){
        var order = new Order();
        order.setUser(user);
        order.setStatus(false);

        return orderRepository.save(order);
    }

    public void finishOrder(UUID idUSer) {
        var user = userRepository.findUserById(idUSer);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var order = createOrder(user);

        var cartByUser = cartRepository.findCartsByIdAndIsActive(idUSer, true);
        if (cartByUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var cartItems = cartItemRepository.findByCartId(cartByUser);
        cartItems.forEach(orderItem -> {
            createOrderItem(order, orderItem);
        });

        cartItems.forEach(cartItem -> {
            createOrderItem(order, cartItem);
        });

        order.setTotalPrice(orderItemRepository.sumPriceByOrderItem(order));
        order.setStatus(true);
        orderRepository.save(order);
    }

    public void createOrderItem(Order order, CartItem cartItem){
        //validar se quantidade do produto é o suficiente
        //diminuiu quantidade

        var product = productRepository.findById(cartItem.getProductId().getId());

        if (product.get().getQuantity() > cartItem.getQuantity()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
            //alterar para o status mais adequado
        }

        var newQuantity = product.get().getQuantity() - cartItem.getQuantity();
        product.get().setQuantity(newQuantity);

        if (product.get().getQuantity() == 0){
            productService.inactivateProduct(product.get().getId(), false);
        }

        var orderItem = new OrderItem();
        orderItem.setOrderId(order);
        orderItem.setProductId(orderItem.getProductId());
        orderItem.setQuantity(orderItem.getQuantity());
        orderItem.setPrice(orderItem.getPrice());

        orderItemRepository.save(orderItem);
    }
}
