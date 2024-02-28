package com.example.shipgofunding.user.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import com.example.shipgofunding.user.domain.RoleEnum;
import com.example.shipgofunding.user.domain.User;

public class UserRequest {

    @Getter
    @Setter
    public static class SignupRequestDTO {
        @NotNull(message = "이메일은 필수 입력 값입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
        private String email;

        @NotNull(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and include at least one letter and one number")
        private String password;

        @NotNull(message = "닉네임은 필수 입력 값입니다.")
        private String nickname;

        public User toEntity(RoleEnum role) {
            return User.builder()
                    .nickname(nickname)
                    .email(email)
                    .password(password)
                    .role(role)
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
