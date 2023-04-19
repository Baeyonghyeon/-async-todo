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
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsManager {

    private final MemberRepository memberRepository;

    @Override
    public void createUser(UserDetails user) {
        log.info("MemberDetailsService try createUser {}", user);

        if(userExists(user.getUsername())){
          throw new UserAlreadyExistAuthenticationException("username exist");
        }

        Member member = Member.of((MemberDetails) user);
        //Todo : Role 생성 실패 exception 만들기. 메소드 분리도 해야할거 같음.
        MemberRole memberRole = MemberRole.of(RoleType.ADMIN, member);
        member.addMemberRole(memberRole);

        memberRepository.save(member);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
