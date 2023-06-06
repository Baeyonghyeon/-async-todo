package com.kurt.asynctodo.config.security.dto.response;

public record MemberSignInResponseDto(
        String username,
        String accessToken,
        String refreshToken
){

}
