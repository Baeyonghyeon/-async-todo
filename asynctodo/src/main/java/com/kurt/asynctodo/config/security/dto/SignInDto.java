package com.kurt.asynctodo.config.security.dto;

public record SignInDto(
        String username,
        String password
) {
}
