package com.andydkeh.ecommercechallenge.repository;

import com.andydkeh.ecommercechallenge.entity.Cart;
import com.andydkeh.ecommercechallenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Cart findCartsByIdUserAndIsActive(User idUser, Boolean isActive);

    Cart findCartsByIdAndIsActive(UUID id, Boolean isActive);
}
