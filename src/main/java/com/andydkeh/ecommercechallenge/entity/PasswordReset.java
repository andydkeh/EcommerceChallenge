package com.andydkeh.ecommercechallenge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
public class PasswordReset {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private OffsetDateTime expiryDate;

    public boolean isExpired() {
        return OffsetDateTime.now().isAfter(this.expiryDate);
    }
}
