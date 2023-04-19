package com.kurt.asynctodo.domain;

import lombok.Getter;

public enum RoleType {
    NORMAL("일반 유저"),
    ADMIN("관리자");

    @Getter
    private final String authority;

    RoleType(String authority) {
        this.authority = authority;
    }
}
