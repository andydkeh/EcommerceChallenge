package com.compass.ecommercechallenge.service;

import com.compass.ecommercechallenge.entity.*;
import com.compass.ecommercechallenge.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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

        var cartByUser = cartRepository.findCartsByIdUserAndIsActive(user, true);
        if (cartByUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var cartItems = cartItemRepository.findByCartId(cartByUser);

        cartItems.forEach(cartItem -> {
            createOrderItem(order, cartItem);
        });

        order.setTotalPrice(cartItemRepository.calculateCartTotal(cartByUser));
        order.setStatus(true);
        orderRepository.save(order);

        cartByUser.setIsActive(false);
        cartRepository.save(cartByUser);

        var newCart = new Cart();
        newCart.setIdUser(user);
        newCart.setIsActive(true);
        cartRepository.save(newCart);
    }

    public void createOrderItem(Order order, CartItem cartItem){
        var product = productRepository.findById(cartItem.getProductId().getId());

        if (product.get().getQuantity() < cartItem.getQuantity()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        var newQuantity = product.get().getQuantity() - cartItem.getQuantity();
        product.get().setQuantity(newQuantity);
        productRepository.save(product.get());

        if (product.get().getQuantity() == 0){
            productService.inactivateProduct(product.get().getId(), false);
        }

        BigDecimal itemTotalPrice = cartItem.getProductId().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        var orderItem = new OrderItem();
        orderItem.setOrderId(order);
        orderItem.setProductId(cartItem.getProductId());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(itemTotalPrice);

        orderItemRepository.save(orderItem);
    }
}
