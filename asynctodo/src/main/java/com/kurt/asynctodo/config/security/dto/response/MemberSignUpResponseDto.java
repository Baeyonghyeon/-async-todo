package com.kurt.asynctodo.config.security.dto.response;

import com.kurt.asynctodo.domain.Member;

public record MemberSignUpResponseDto(
        String username,
        String password
) {
    public static MemberSignUpResponseDto from(Member member) {
        return new MemberSignUpResponseDto(
                member.getUsername(),
                member.getPassword()
        );
    }
}
