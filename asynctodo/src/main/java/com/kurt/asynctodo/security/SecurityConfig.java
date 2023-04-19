package com.kurt.asynctodo.security;

import com.kurt.asynctodo.security.handler.MemberAccessDeniedHandler;
import com.kurt.asynctodo.security.handler.MemberAuthenticationEntryPoint;
import com.kurt.asynctodo.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

//    private final DefaultOAuth2UserService userService;
    private final JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .headers(h -> h.frameOptions().sameOrigin())
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(withDefaults()) // withDefaults()일 경우 corsConfigurationSource 이름으로 등록된 Bean 이용
                    .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .formLogin(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .exceptionHandling(e -> {
                        e.authenticationEntryPoint(new MemberAuthenticationEntryPoint());
                        e.accessDeniedHandler(new MemberAccessDeniedHandler());
                    })
                    .apply(new CustomFilterConfigDSL(jwtUtils)); // 직접 구현한 JwtAuthenticationFilter 등록

            return http.build();
    }

    /*
     * Form Login시 걸리는 Filter bean register
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // 쿠키가능.
        configuration.setAllowedOrigins(List.of(
                        "http://localhost:8080"
                )
        ); // * 은 문제 발생
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "UPDATE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Refresh");
        configuration.setMaxAge(3000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // UrlBasedCorsConfigurationSource 생성
        source.registerCorsConfiguration("/**", configuration); // 모든 URL에 앞에서 구성한 CORS 정책 적용

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}