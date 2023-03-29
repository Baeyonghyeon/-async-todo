package com.kurt.asynctodo.security.dto;

import com.kurt.asynctodo.domain.Member;
import com.kurt.asynctodo.domain.MemberRole;
import com.kurt.asynctodo.domain.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public record MemberDetails(
        Long memberId,
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails{

    public static MemberDetails of(Long userId, String username, String password, List<MemberRole> userRoles) {
        return new MemberDetails(
                userId,
                username,
                password,
                userRoles.stream()
                        .map(MemberRole::getRole)
                        .map(RoleType::getAuthority)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public static MemberDetails from(Member member) {
        return MemberDetails.of(
                member.getId(),
                member.getUsername(),
                member.getPassword(),
                member.getMemberRoles()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
