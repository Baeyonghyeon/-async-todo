package com.kurt.asynctodo.security.service;

import com.kurt.asynctodo.domain.Member;
import com.kurt.asynctodo.domain.MemberRole;
import com.kurt.asynctodo.domain.RoleType;
import com.kurt.asynctodo.exception.UserAlreadyExistAuthenticationException;
import com.kurt.asynctodo.repository.MemberRepository;
import com.kurt.asynctodo.security.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsManager {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails user) {
        log.info("MemberDetailsService try createUser {}", user);
        log.info("MemberDetailsService try createUser for memberDetails {}", user);

        if (userExists(user.getUsername())) {
            throw new UserAlreadyExistAuthenticationException("username exist");
        }

        Member member = Member.of((MemberDetails) user, passwordEncoder);
        //Todo : Role 생성 메소드 분리.
        MemberRole memberRole = MemberRole.of(RoleType.ADMIN, member);
        member.addMemberRole(memberRole);

        memberRepository.save(member);

        log.info("SignUpUsername : {}", member.getUsername());
        log.info("SignUpPassword : {}", member.getPassword());
    }

    @Override
    public void updateUser(UserDetails user) {
        log.info("MemberDetailsService try updateUser {}", user);

    }

    @Override
    public void deleteUser(String username) {
        log.info("MemberDetailsService try deleteUser {}", username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        log.info("MemberDetailsService try changePassword");
    }

    @Override
    public boolean userExists(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        return memberRepository.findByUsername(username)
                .map(MemberDetails::from)
                .orElseThrow(() -> new UsernameNotFoundException("UsernameNotFound"));
    }
}
