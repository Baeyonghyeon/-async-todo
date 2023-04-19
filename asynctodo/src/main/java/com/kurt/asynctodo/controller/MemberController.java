package com.kurt.asynctodo.controller;

import com.kurt.asynctodo.security.dto.MemberDetails;
import com.kurt.asynctodo.security.dto.SignInDto;
import com.kurt.asynctodo.security.dto.SignUpDto;
import com.kurt.asynctodo.security.service.MemberDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberDetailsService memberDetailsService;

    @GetMapping
    public void SignIn(@Valid SignInDto signInDto) {

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void SignUp(@Valid SignUpDto signUpDto) {
        MemberDetails memberDetails = MemberDetails.from(signUpDto);

        memberDetailsService.createUser(memberDetails);
    }


}
