package com.example.To_Do_App.dto;

public record ErrorResponseDto(
        int status,
        String message,
        String timestamp
) {
}
