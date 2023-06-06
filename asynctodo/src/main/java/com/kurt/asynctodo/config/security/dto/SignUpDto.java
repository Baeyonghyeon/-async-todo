package com.kurt.asynctodo.security.dto;

import jakarta.validation.constraints.NotNull;

public record SignUpDto(
        @NotNull
        String username,
        @NotNull
        String password
) {
}
