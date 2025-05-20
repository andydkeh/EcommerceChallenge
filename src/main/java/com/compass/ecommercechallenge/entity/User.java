package com.compass.ecommercechallenge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role;

    public enum Values {
        ADMIN(1),
        CLIENT(2);

        long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }
    }
}
