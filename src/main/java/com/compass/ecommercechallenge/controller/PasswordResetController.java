package com.compass.ecommercechallenge.controller;

import com.compass.ecommercechallenge.dto.security.ForgotPasswordDTO;
import com.compass.ecommercechallenge.dto.security.MessageResponseDTO;
import com.compass.ecommercechallenge.dto.security.ResetPasswordDTO;
import com.compass.ecommercechallenge.service.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponseDTO> forgotPassword(
            @Valid @RequestBody ForgotPasswordDTO request) {

        passwordResetService.requestPasswordReset(request.email());

        return ResponseEntity.ok(new MessageResponseDTO(
                "Se o email existir, você receberá instruções para redefinir a senha"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponseDTO> resetPassword(
            @Valid @RequestBody ResetPasswordDTO request) {

        passwordResetService.resetPassword(request.token(), request.newPassword());

        return ResponseEntity.ok(new MessageResponseDTO(
                "Senha alterada com sucesso"));
    }

    @GetMapping("/validate-token")
    public ResponseEntity<MessageResponseDTO> validateToken(
            @RequestParam String token) {

        return ResponseEntity.ok(new MessageResponseDTO("Token válido"));
    }
}