package com.example.shipgofunding.user.domain;



public enum RoleEnum {
    USER("일반사용자"),
    ADMIN("일반관리자");
    private final String description;

    RoleEnum (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
