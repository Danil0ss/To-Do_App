package com.example.To_Do_App.dto;

public record TaskDto(
        Long id,
        String title,
        String description,
        Boolean completed,
        Integer position
) {
}
