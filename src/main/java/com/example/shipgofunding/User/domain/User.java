package com.example.shipgofunding.domain;

import com.example.shipgofunding.domain.UserRole.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity

public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //컬럼 설정
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name="role")
    private RoleEnum role;

    //권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Builder
    public User(String email, String password, String nickname, RoleEnum role) {
        this.email = email;
        this.password = password;
        this.nickname=nickname;
        this.role = role;
    }

    //TO DO: UserDetail 상속 제거
    //TO DO: 데이터 베이스 설정 수정

    @CreationTimestamp
    private Timestamp createData;


}