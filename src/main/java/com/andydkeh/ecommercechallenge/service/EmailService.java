package com.andydkeh.ecommercechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    public void sendPasswordResetEmail(String email, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[Ecommerce Challenge] Redefinição de senha");
            message.setText(
                    "Para redefinir sua senha, copie o token abaixo e envie uma request para 'reset-password' com a nova senha:\n\n" +
                            token + "\n\n" +
                            "Este token expira em 24 horas.\n\n" +
                            "Se você não solicitou esta alteração, ignore este email."
            );

            mailSender.send(message);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao enviar email");
        }
    }
}
