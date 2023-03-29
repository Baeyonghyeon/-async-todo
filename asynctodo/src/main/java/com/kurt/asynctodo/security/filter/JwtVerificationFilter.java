package com.kurt.asynctodo.security.filter;

import com.kurt.asynctodo.security.JwtUtils;
import com.kurt.asynctodo.security.dto.MemberInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JWTtoken 이 존재하는지 검사
 */
@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT 토큰 검증 및 SecurityContent에 저장을 시도합니다.");
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {

        String jws = request.getHeader("Authorization").replace("Bearer ", "");

        // Secret Key 획득
        String base64EncodedSecretKey = jwtUtils.encodeBase64SecretKey(jwtUtils.getSecretKey());

        // JWT에서 claims가 정상적으로 파싱되면 검증 성공
        log.info("JWT에서 claims가 정상적으로 파싱 검증 성공했습니다. {}", jwtUtils.getClaims(jws, base64EncodedSecretKey).getBody());
        return jwtUtils.getClaims(jws, base64EncodedSecretKey).getBody();
    }

    /**
     * SecurityContext에 저장하기 위한 메서드
     */
    private void setAuthenticationToContext(Map<String, Object> claims) {
        for (Map.Entry<String, Object> entrySet : claims.entrySet()) {
            log.info(entrySet.getKey() + " : " + entrySet.getValue());
        }

        // username을 얻음
        String username = (String) claims.get("username");
        // Users id를 얻음
        Long userId = Long.valueOf((Integer) claims.get("userId"));
        // profile id 를 얻음
        Long profileId = Long.valueOf((Integer) claims.get("profileId"));
        // 권한 정보를 얻음
        Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) ((List) claims.get("roles")).stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toUnmodifiableSet());

        log.info("authorities : {} ", authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                MemberInfo.of(userId, username, authorities), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
