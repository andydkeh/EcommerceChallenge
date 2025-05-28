package com.compass.ecommercechallenge.service;

import com.compass.ecommercechallenge.dto.cart.CartEditQuantityDTO;
import com.compass.ecommercechallenge.dto.cart.CartItemDTO;
import com.compass.ecommercechallenge.dto.cart.ReadCartItemDTO;
import com.compass.ecommercechallenge.dto.product.ReadProductDTO;
import com.compass.ecommercechallenge.entity.Cart;
import com.compass.ecommercechallenge.entity.CartItem;
import com.compass.ecommercechallenge.entity.User;
import com.compass.ecommercechallenge.repository.CartItemRepository;
import com.compass.ecommercechallenge.repository.CartRepository;
import com.compass.ecommercechallenge.repository.ProductRepository;
import com.compass.ecommercechallenge.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public List<ReadCartItemDTO> getCartItems(UUID id) {

        var user = userRepository.findUserById(id);
        var cart = cartRepository.findCartsByIdUserAndIsActive(user, true);

        var finalCart = cartItemRepository.findByCartId(cart);

        if (finalCart.isEmpty()) {
            return List.of();
        }

        return finalCart.stream()
                .map(cartItem -> new ReadCartItemDTO(
                        cartItem.getCartId().getId(),
                        cartItem.getProductId().getId(),
                        cartItem.getId(),
                        cartItem.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    public CartService(CartRepository cartRepository, UserRepository userRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    //roda primeiro quanod cria user
    public void createCart(User user) {
        var cart = new Cart();
        cart.setIdUser(user);
        cartRepository.save(cart);
    }

    public void addItemsCart(UUID idUser, List<CartItemDTO> cartItemsDTO){

        //VALIDAR SE PRODUTO EXISTE E SE TEM NOE STOQUE
        //valida se ja tem no carrinho
        var idUserCart = userRepository.findUserById(idUser);
        var cartActive = cartRepository.findCartsByIdUserAndIsActive(idUserCart, true);

        cartItemsDTO.forEach(item -> createCartItem(cartActive, item));
    }

    public void createCartItem(Cart idCart, CartItemDTO dto){
        var productExists = productRepository.findById(dto.idProduct());

        if (productExists.isPresent() && productExists.get().getQuantity() >= dto.quantity()) {
            var alreadyExists = cartItemRepository.findCartItemByCartIdAndProductId(idCart, productExists.get());
            if (alreadyExists != null){
                var cartItem = new CartItem();
                cartItem.setCartId(idCart);
                cartItem.setProductId(productExists.get());
                cartItem.setQuantity(dto.quantity());
                cartItemRepository.save(cartItem);
            }
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //valida se carrinho ta aberto > valida se iteme xiste
    public void deleteItemsCart(UUID idItem){
        var idItemExists = cartItemRepository.findById(idItem);
        if (idItemExists.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var cartExists = cartRepository.findCartsByIdAndIsActive(idItemExists.get().getCartId().getId(), true);

        if (cartExists != null){
            cartItemRepository.delete(idItemExists.get());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    //valida se iteme xiste, se carrinho ta ativo e se o carrinho pertence ao user
    public void editQuantityItem(UUID idUser, CartEditQuantityDTO cartEditQuantityDTO){
        var idItemExists = cartItemRepository.findById(cartEditQuantityDTO.idItem());

        if (idItemExists.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var cartExists = cartRepository.findCartsByIdAndIsActive(idItemExists.get().getCartId().getId(), true);

        var itemUser = cartRepository.findCartsByIdUserAndIsActive(userRepository.findUserById(idUser), true);

        if (cartExists != null && itemUser != null){
            var cartItem = cartItemRepository.findById(cartEditQuantityDTO.idItem());
            cartItem.get().setQuantity(cartEditQuantityDTO.quantity());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    }
}
