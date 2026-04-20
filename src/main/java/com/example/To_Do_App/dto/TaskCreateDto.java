package com.example.To_Do_App.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record TaskCreateDto(
        @NotBlank(message = "Title must not be empty")
        String title,

        String description
) {
}
