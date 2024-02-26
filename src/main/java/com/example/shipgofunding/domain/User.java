package com.example.shipgofunding.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
// UserDetails를 상속받아 인증 객체로 사용
public class User implements UserDetails {
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

    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Builder
    public User(String email, String password, String auth) {
        this.email = email;
        this.password = password;
        this.nickname=nickname;
    }

    // 사용자의 id를 반환 (고유 값)
    @Override
    public String getUsername() {
        return email;
    }



    @Override
    public String getPassword() {
        return password;
    }


    public String getNickname() {
        return nickname;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true;  // true -> 만료 X
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금 여부 확인 로직
        return true;  // true -> 잠금 X
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드 만료 여부 확인
        return true;  // true -> 만료 X
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return  true;  // true -> 사용 가능
    }
}