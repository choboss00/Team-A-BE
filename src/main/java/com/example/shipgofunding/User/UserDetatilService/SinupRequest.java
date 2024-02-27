package com.example.shipgofunding.UserDetatilService;

import com.example.shipgofunding.domain.User;
import com.example.shipgofunding.domain.UserRole.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;


//사용자 정보 담을 dto
//유효성 검증
@NoArgsConstructor
@Getter
public class SinupRequest {
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    private RoleEnum role;

   //dto 생성
    public User toEntity() {
        return User.builder()
                .role(role)
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();
    }

}
