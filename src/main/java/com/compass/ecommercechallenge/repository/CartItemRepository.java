package com.compass.ecommercechallenge.repository;

import com.compass.ecommercechallenge.entity.Cart;
import com.compass.ecommercechallenge.entity.CartItem;
import com.compass.ecommercechallenge.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

   List<CartItem> findByCartId(Cart cart);
   
   CartItem findCartItemByCartIdAndProductId(Cart cartId, Product productId);
}
