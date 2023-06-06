package com.kurt.asynctodo.security.dto.response;

public record MemberSignInResponseDto(
        String username,
        String accessToken,
        String refreshToken
){

}
