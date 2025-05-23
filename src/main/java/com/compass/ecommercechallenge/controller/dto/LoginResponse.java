package com.compass.ecommercechallenge.controller.dto;

public record LoginResponse(String accessToken, long expiresIn) {
}
