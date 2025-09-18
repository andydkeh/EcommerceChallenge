package com.andydkeh.ecommercechallenge.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ForgotPasswordDTO(@Email String email) {}
