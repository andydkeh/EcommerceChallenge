package com.andydkeh.ecommercechallenge.config;

import com.andydkeh.ecommercechallenge.utils.UserRoleEnum;
import com.andydkeh.ecommercechallenge.entity.User;
import com.andydkeh.ecommercechallenge.repository.RoleRepository;
import com.andydkeh.ecommercechallenge.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(UserRoleEnum.ADMIN.name());

        var userAdmin = userRepository.findByEmail("admin@admin.com");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var user = new User();
                    user.setEmail("admin@admin.com");
                    user.setPassword(passwordEncoder.encode("senha123"));
                    user.setRole(Set.of(roleAdmin));
                    userRepository.save(user);
                }
        );
    }
}