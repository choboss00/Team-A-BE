package com.example.shipgofunding.domain;

import com.example.shipgofunding.domain.UserRole.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
//login 기능을 SpringSecurity에 상속하기 위해 UserDetails 사용
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

    //사용자의 고유값 반환
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; //true : 만료 X
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; //true : 잠금 X
    }

    //패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;  //true : 만료 X
    }
    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true; // true : 사용 가능
    }



}