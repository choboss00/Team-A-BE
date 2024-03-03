package com.example.shipgofunding.user.request;


import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.shipgofunding.user.domain.RoleEnum;
import com.example.shipgofunding.user.domain.User;
import org.springframework.stereotype.Component;



public class UserRequest {

    @Getter
    @Setter
    @Component
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
    @NoArgsConstructor
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

    @NoArgsConstructor
    @Getter
    public static class SendEmailRequestDTO {
        @NotNull(message = "이메일은 필수 입력 값입니다.")
        private String email;

        public SendEmailRequestDTO(String email) {
            this.email = email;
        }
    }
    @NoArgsConstructor
    @Getter
    @Setter
    public static class VerficationRequestDTO{
        @NotNull(message =  "이메일은 필수 입력 값입니다.")
        private String email;

        @NotNull(message = "인증코드는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{1,6}$", message = "Password must be a combination of letters and numbers, and should be between 1 and 6 characters long.")
        private String usercode;

        public VerficationRequestDTO (String email, String usercode){
            this.email=email;
            this.usercode=usercode;
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class PasswordDTO {
        @NotNull(message =  "이메일은 필수 입력 값입니다.")
        private String email;

        @NotNull(message = "비밀번호는 필수 입력 값 입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and include at least one letter and one number")
        private String password;

        @NotNull(message = "비밀번호를 한 번 더 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and include at least one letter and one number")
        private String passwordcheck;

        public PasswordDTO(String password, String passwordcheck, String email) {
            this.password = password;
            this.passwordcheck = passwordcheck;
            this.email=email;
        }
    }



}
