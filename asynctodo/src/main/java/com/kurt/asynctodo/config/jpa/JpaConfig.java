package com.kurt.asynctodo.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
//        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .map(MemberInfo.class::cast)
//                .map(MemberInfo::username);
        // Todo : 로그인 사용자는 로그인한 아이디가 표시되게, 로그인을 하지 않으면 임이의 값을 정해 넣는다.
        return () -> Optional.of("kurt");
    }

}
