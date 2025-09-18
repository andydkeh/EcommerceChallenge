package com.andydkeh.ecommercechallenge.repository;

import com.andydkeh.ecommercechallenge.entity.PasswordReset;
import com.andydkeh.ecommercechallenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
    PasswordReset findByToken(String token);
    PasswordReset findByUser(User user);
    void deleteByUser(User user);
}
