package com.compass.ecommercechallenge.dto;

import jakarta.validation.constraints.Email;

public record CreteUserDTO(@Email String email,
                           String password) {
}
