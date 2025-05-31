package com.compass.ecommercechallenge.dto.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordDTO(
        @NotBlank String token,
        @NotBlank @Size(min = 6) String newPassword
) {}