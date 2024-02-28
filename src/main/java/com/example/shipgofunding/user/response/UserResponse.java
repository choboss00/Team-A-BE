package com.example.shipgofunding.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {

        @Getter
        @Setter
        public static class LoginResponseDTO {
                @Schema(description = "회원 이메일", example = "example@example.com")
                String email;

                @Schema(description = "회원 닉네임", example = "choboss00")
                String nickname;

                @Schema(description = "토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2xhYmVhciIsImlhdCI6MTYyNzIwNzIwMCwiZXhwIjoxNjI3MjA3MjAwfQ.")
                String token;

                public LoginResponseDTO(String email, String nickname, String token) {
                        this.email = email;
                        this.nickname = nickname;
                        this.token = token;
                }

        }
}
