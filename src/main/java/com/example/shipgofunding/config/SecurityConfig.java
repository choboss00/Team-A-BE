package com.example.shipgofunding.config;

import com.example.shipgofunding.config.jwt.JWTAuthenticationEntryPoint;
import com.example.shipgofunding.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig   {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;	// JwtAuthenticationFilter 주입
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;	// JWTAuthenticationEntryPoint 주입

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    // CORS 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // 모든 출처 허용
        configuration.addAllowedHeader("*"); // 모든 요청 헤더 허용
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addExposedHeader("Authorization"); // "Authorization" 응답 헤더 노출
        configuration.setAllowCredentials(true); // 쿠키 및 인증 정보 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 X
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource())) // CORS 설정
                .formLogin(AbstractHttpConfigurer::disable) // form login 사용 X
                .httpBasic(AbstractHttpConfigurer::disable) // http basic 사용 X
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/my-page/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/fundings/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/fundings/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/fundings/**").authenticated()
                        .anyRequest().permitAll()) // 모든 요청 허용
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)) // jwt 인증 예외 처리
                .build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

}
