package com.kurt.asynctodo.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurt.asynctodo.config.security.dto.MemberDetails;
import com.kurt.asynctodo.config.security.utils.JwtUtils;
import com.kurt.asynctodo.config.security.dto.SignInDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    //인증 시도
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("JwtAuthenticationFilter : 로그인 시도.");
        ObjectMapper mapper = new ObjectMapper();
        SignInDto signInDto = mapper.readValue(request.getInputStream(), SignInDto.class);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInDto.username(), signInDto.password());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("authResult.getPrincipal value = {}", authResult.getPrincipal());
        MemberDetails memberDetails = (MemberDetails) authResult.getPrincipal();

        String accessToken = delegateAccessToken(memberDetails);
        String refreshToken = delegateRefreshToken(memberDetails);
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    private String delegateAccessToken(MemberDetails memberDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", memberDetails.username());
        claims.put("userId", memberDetails.memberId());
        claims.put("roles", memberDetails.authorities());

        String subject = memberDetails.getUsername(); // JWT 제목
        Date expiration = jwtUtils.getTokenExpiration(jwtUtils.getAccessTokenExpirationMinutes()); // 토큰 발행 일자
        String base64EncodedSecretKey = jwtUtils.encodeBase64SecretKey(jwtUtils.getSecretKey()); // Secret Key 문자열 인코딩

        return jwtUtils.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }

    private String delegateRefreshToken(MemberDetails memberDetails) {
        String subject = memberDetails.getUsername();
        Date expiration = jwtUtils.getTokenExpiration(jwtUtils.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtUtils.encodeBase64SecretKey(jwtUtils.getSecretKey());

        return jwtUtils.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
    }
}
