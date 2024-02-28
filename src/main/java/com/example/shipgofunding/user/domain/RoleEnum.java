package com.example.shipgofunding.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    USER("USER","일반사용자"),
    ADMIN("ADMIN","일반관리자");
    private final String key;
    private final String title;
}
