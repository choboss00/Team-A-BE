package com.example.shipgofunding.product.domain;

public enum FundingEnum {
    OPEN_SCHEDULED("오픈 예정"),
    IN_PROGRESS("진행 중"),
    FUNDING_CLOSED("마감된 펀딩"),
    CLOSE_IMMINENT("마감 임박");

    private final String description;

    FundingEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

