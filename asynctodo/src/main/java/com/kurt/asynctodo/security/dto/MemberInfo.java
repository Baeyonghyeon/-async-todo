package com.kurt.asynctodo.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record MemberInfo(
        Long memberId,
        String username,
        Collection<? extends GrantedAuthority> authorities
) {

    public static MemberInfo of(Long memberId, String username, Collection<? extends GrantedAuthority> authorities) {

        return new MemberInfo(
                memberId,
                username,
                authorities);
    }
}
