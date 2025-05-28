package com.compass.ecommercechallenge.service;

import com.compass.ecommercechallenge.dto.order.CreateOrderItemDTO;
import com.compass.ecommercechallenge.entity.CartItem;
import com.compass.ecommercechallenge.entity.Order;
import com.compass.ecommercechallenge.entity.OrderItem;
import com.compass.ecommercechallenge.repository.CartRepository;
import com.compass.ecommercechallenge.repository.OrderItemRepository;
import com.compass.ecommercechallenge.repository.OrderRepository;
import com.compass.ecommercechallenge.repository.ProductRepository;
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

    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository,
                        OrderItemRepository orderItemRepository,
                        ProductRepository productRepository,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    public void finishOrder(UUID idUSer) {
        var cartByUser = cartRepository.findCartsByIdAndIsActive(idUSer, true);
        if (cartByUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //chamar aqui o createOrdemItem

        orderRepository.save(order);
    }

    public void createOrderItem(CreateOrderItemDTO createOrderItemDTO){
        //validar se quantidade do produto é o suficiente
        //diminuiu quantidade

        var product = productRepository.findById(createOrderItemDTO.productId().getId());

        if (product.get().getQuantity() > createOrderItemDTO.quantity()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
            //alterar para o status mais adequado
        }
        var newQuantity = product.get().getQuantity() - createOrderItemDTO.quantity();
        product.get().setQuantity(newQuantity);

        if (product.get().getQuantity() == 0){
            productService.inactivateProduct(product.get().getId(), false);
        }

        var orderItem = new OrderItem();
        orderItem.setOrderId(createOrderItemDTO.orderId());
        orderItem.setProductId(createOrderItemDTO.productId());
        orderItem.setQuantity(createOrderItemDTO.quantity());
        orderItem.setPrice(createOrderItemDTO.price());

        orderItemRepository.save(orderItem);

    }
}
