package com.kurt.asynctodo.controller;

import com.kurt.asynctodo.config.security.dto.MemberDetails;
import com.kurt.asynctodo.config.security.dto.SignUpDto;
import com.kurt.asynctodo.config.security.service.MemberDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberDetailsService memberDetailsService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signup")
    public void SignUp(@Valid @RequestBody SignUpDto signUpDto) {
        UserDetails userDetails = MemberDetails.from(signUpDto);

        memberDetailsService.createUser(userDetails);
    }
}
