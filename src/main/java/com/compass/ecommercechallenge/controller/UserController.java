package com.compass.ecommercechallenge.controller;

import com.compass.ecommercechallenge.UserRoleEnum;
import com.compass.ecommercechallenge.controller.dto.CreteUserDTO;
import com.compass.ecommercechallenge.entity.User;
import com.compass.ecommercechallenge.repository.RoleRepository;
import com.compass.ecommercechallenge.repository.UserRepository;
import com.compass.ecommercechallenge.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    @PostMapping("/createUser")
    public ResponseEntity<Void> newUser(@RequestBody CreteUserDTO newUserDTO) {

        var clientRole = roleRepository.findByName(UserRoleEnum.CLIENT.name());
        var userExists = userRepository.findByEmail(newUserDTO.email());
        if (userExists.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setEmail(newUserDTO.email());
        user.setPassword(bCryptPasswordEncoder.encode(newUserDTO.password()));
        user.setRole(Set.of(clientRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/createAdmin")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> newAdmin(@RequestBody CreteUserDTO newUserDTO) {

        var clientRole = roleRepository.findByName(UserRoleEnum.ADMIN.name());
        var userExists = userRepository.findByEmail(newUserDTO.email());
        if (userExists.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setEmail(newUserDTO.email());
        user.setPassword(bCryptPasswordEncoder.encode(newUserDTO.password()));
        user.setRole(Set.of(clientRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}