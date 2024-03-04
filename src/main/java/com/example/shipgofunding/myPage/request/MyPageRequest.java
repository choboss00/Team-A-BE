package com.example.shipgofunding.myPage.request;

import lombok.Getter;
import lombok.Setter;

public class MyPageRequest {

    @Getter
    @Setter
    public static class MyPageUpdateRequestDTO {
        private String imageUrl;
        private String nickname;
    }
}
