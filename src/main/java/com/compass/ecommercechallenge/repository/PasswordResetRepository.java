package com.compass.ecommercechallenge.repository;

import com.compass.ecommercechallenge.entity.PasswordReset;
import com.compass.ecommercechallenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
    PasswordReset findByToken(String token);
    PasswordReset findByUser(User user);
    void deleteByUser(User user);
}
