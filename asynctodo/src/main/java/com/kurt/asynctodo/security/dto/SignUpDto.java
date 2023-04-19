package com.kurt.asynctodo.security.dto;

public record SignUpDto(
        Long id,
        String username,
        String password
) {
}
