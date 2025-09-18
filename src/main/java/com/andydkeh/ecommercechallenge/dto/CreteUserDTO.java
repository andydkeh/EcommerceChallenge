package com.andydkeh.ecommercechallenge.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreteUserDTO(@Email String email,
                           @NotBlank @Size(min = 6, message = "password must have at least 6 characters")String password) {
}
