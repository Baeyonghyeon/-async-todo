package com.kurt.asynctodo.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyExistAuthenticationException extends RuntimeException{

    public UserAlreadyExistAuthenticationException(String message) {
        log.debug("아이디가 존재해요");
    }
}
