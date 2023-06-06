package com.kurt.asynctodo.config.security;

import com.kurt.asynctodo.config.security.filter.JwtAuthenticationFilter;
import com.kurt.asynctodo.config.security.filter.JwtVerificationFilter;
import com.kurt.asynctodo.config.security.handler.MemberAuthenticationFailureHandler;
import com.kurt.asynctodo.config.security.utils.JwtUtils;
import com.kurt.asynctodo.config.security.handler.MemberAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@RequiredArgsConstructor
public class CustomFilterConfigDSL extends AbstractHttpConfigurer<CustomFilterConfigDSL, HttpSecurity> {

    private final JwtUtils jwtUtils;

    @Override
    public void configure(HttpSecurity builder) {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtUtils);
        JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtUtils);

        jwtAuthenticationFilter.setFilterProcessesUrl("/members/signin");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

        builder.addFilter(jwtAuthenticationFilter)
                .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
    }
}
