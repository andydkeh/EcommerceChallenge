package com.compass.ecommercechallenge.controller;

import com.compass.ecommercechallenge.dto.cart.CartItemDTO;
import com.compass.ecommercechallenge.dto.cart.ReadCartItemDTO;
import com.compass.ecommercechallenge.dto.product.ReadProductDTO;
import com.compass.ecommercechallenge.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) { this.cartService = cartService; }

    @GetMapping("/cart")
    public ResponseEntity<?> readCart(@AuthenticationPrincipal Jwt jwt) {
        var idUserToken = UUID.fromString(jwt.getClaim("sub"));
        List<ReadCartItemDTO> cartItems = cartService.getCartItems(idUserToken);

        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no items in cart");
        } else {
            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        }
    }

    @PostMapping("/addItemsCart")
    public ResponseEntity<Void> addItemsCart(@AuthenticationPrincipal Jwt jwt, @RequestBody List<CartItemDTO> items){
        var idUserToken = UUID.fromString(jwt.getClaim("sub"));
        cartService.addItemsCart(idUserToken, items);

        return ResponseEntity.ok().build();
    }

    //testar depois se ta deletando mesmo com o cart fechado
    @PostMapping("/deleteItemCart/{id}")
    public ResponseEntity<Void> deleteItemCart(@PathVariable UUID id){
        cartService.deleteItemsCart(id);

        return ResponseEntity.ok().build();
    }
}
