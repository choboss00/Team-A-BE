package com.example.shipgofunding.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.example.shipgofunding.user.domain.RoleEnum;
import com.example.shipgofunding.user.domain.User;

public class UserRequest {

    @Getter
    @Setter
    public static class SignupRequestDTO {
        @NotNull(message = "닉네임은 필수 입력 값입니다.")
        private String nickname;

        @NotNull(message = "비밀번호는 필수 입력 값입니다.")
        private String password;

        @NotNull(message = "이메일은 필수 입력 값입니다.")
        private String email;

        private RoleEnum role;

        public User toEntity() {
            return User.builder()
                    .role(role)
                    .nickname(nickname)
                    .email(email)
                    .password(password)
                    .build();
        }

    }

    @Getter
    @Setter
    public static class LoginRequestDTO {
        @NotNull(message = "이메일은 필수 입력 값입니다.")
        private String email;

        @NotNull(message = "비밀번호는 필수 입력 값입니다.")
        private String password;

        public LoginRequestDTO(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
