package com.kurt.asynctodo.config.security.dto;

import jakarta.validation.constraints.NotNull;

public record SignUpDto(
        @NotNull
        String username,
        @NotNull
        String password
) {
}
