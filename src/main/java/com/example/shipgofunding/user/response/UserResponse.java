package com.example.shipgofunding.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {

        @Getter
        @Setter
        public static class SignupResponseDTO {
                @Schema(description = "이메일", example = "example@example.com")
                String email;

                @Schema(description = "비밀번호", example = "$2a$10$5X9R/f1lQCKyzVxwswZqJOaOagB8zR5TOJi/63npMa6JTqVgr5iwq\n")
                String password;

                @Schema(description = "닉네임", example = "choboss00")
                String nickname;

                public SignupResponseDTO(String email, String password, String nickname) {
                        this.email = email;
                        this.password = password;
                        this.nickname = nickname;
                }

        }

        @Getter
        @Setter
        public static class LoginResponseWithTokenDTO {
                @Schema(description = "로그인 응답", implementation = LoginResponseDTO.class)
                private LoginResponseDTO loginResponseDTO;
                @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaG9ib3NzMDAiLCJpYXQiOjE2MzIwNzIwMzMsImV4cCI6MTYzMjA3NTYzM30.")
                private String token;

                public LoginResponseWithTokenDTO(LoginResponseDTO loginResponseDTO, String token) {
                        this.loginResponseDTO = loginResponseDTO;
                        this.token = token;
                }
        }

        @Getter
        @Setter
        public static class LoginResponseDTO {

                @Schema(description = "회원 이메일", example = "example@example.com")
                String email;

                @Schema(description = "회원 닉네임", example = "choboss00")
                String nickname;

                @Schema(description = "회원 프로필 이미지", example = "https://example.com/example.jpg")
                String profileImage;

                @Schema(description = "유저 아이디")
                Long id;

                public LoginResponseDTO(String email, String nickname, String profileImage,Long id) {
                        this.email = email;
                        this.nickname = nickname;
                        this.profileImage = profileImage;
                        this.id=id;
                }

        }

        @Getter
        @Setter
        public static class VerficationResponseDTO{
                @Schema(description = "회원 이메일", example = "tess@test.com")
                String email;

                public VerficationResponseDTO(String email){
                        this.email=email;
                }

        }


}
