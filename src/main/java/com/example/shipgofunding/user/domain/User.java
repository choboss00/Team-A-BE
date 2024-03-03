package com.example.shipgofunding.user.domain;

import com.example.shipgofunding.config.utils.MetaData;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Entity
@Table(name = "users")
public class User extends MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "image")
    private String image;
    // 만약 nickname 이 유니크하다면, service 로직에서 닉네임 중복이 안되도록 수정해줘야 함
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private RoleEnum role;

    //sns 인증 로그인 사용
    @Column(name="username")
    private String username;


    @Builder
    public User(String email, String password, String nickname, RoleEnum role) {
        this.role = role;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
    @Builder
    //비밀번호 변경
    public void setPassword(String newPassword) {
        this.email=email;
        this.password = newPassword;
    }



}