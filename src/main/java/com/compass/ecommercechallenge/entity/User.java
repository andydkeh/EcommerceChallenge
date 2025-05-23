package com.compass.ecommercechallenge.entity;

import com.compass.ecommercechallenge.controller.dto.LoginRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<Role> role;

    public enum Values {
        ADMIN(0),
        CLIENT(1);

        long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }
    }

    public boolean isLoginCorret(LoginRequest loginRequest, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}
