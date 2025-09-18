package com.andydkeh.ecommercechallenge.repository;

import com.andydkeh.ecommercechallenge.entity.Cart;
import com.andydkeh.ecommercechallenge.entity.CartItem;
import com.andydkeh.ecommercechallenge.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

   List<CartItem> findByCartId(Cart cart);
   
   CartItem findCartItemByCartIdAndProductId(Cart cartId, Product productId);

   @Query("SELECT SUM(ci.quantity * p.price) FROM CartItem ci JOIN ci.productId p WHERE ci.cartId = :cart")
   BigDecimal calculateCartTotal(@Param("cart") Cart cart);
}
