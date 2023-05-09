package com.kurt.asynctodo.security.provider;

import com.kurt.asynctodo.security.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomUsernamePasswordProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("CustomUsernamePasswordProvider");

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String username = token.getName();
        String password = token.getCredentials().toString();
        log.info("login username : {}", username);
        log.info("login password : {}", password);

        MemberDetails m = (MemberDetails) userDetailsService.loadUserByUsername(username);
        log.info("MemberDetails : {}", m);

        if (passwordEncoder.matches(password, m.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, m.getAuthorities());
        }
        throw new BadCredentialsException("Something went wrong!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
