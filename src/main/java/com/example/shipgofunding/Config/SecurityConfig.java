package com.example.shipgofunding.Config;

import com.example.shipgofunding.UserDetatilService.UserDetailservice;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final UserDetailservice userDetailservice;

    // 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf ->csrf.disable()) //csrf 비활성화
                .headers(headerConfig->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()))
            .authorizeHttpRequests(auth -> auth // 인증 인가 설정
                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                    .requestMatchers("/login", "/signup", "/user").permitAll()
                    .anyRequest().authenticated());
        return http.build();

    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
