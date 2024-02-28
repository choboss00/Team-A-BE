package com.example.shipgofunding.config.jwt;

import com.example.shipgofunding.config.auth.PrincipalUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Order(0)
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private final PrincipalUserDetailsService principalUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter(TokenProvider tokenProvider, PrincipalUserDetailsService principalUserDetailsService) {
        this.tokenProvider = tokenProvider;
        this.principalUserDetailsService = principalUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = parseBearerToken(request); // 토큰 검증
        String subject = Optional.ofNullable(token)
                .filter(t -> !t.isBlank())
                .map(tokenProvider::validateTokenAndGetSubject) // Token에서 subject 추출
                .orElse(null);

        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 인증 객체 생성 및 SecurityContext에 저장
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) getAuthentication(subject);
            authentication.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(String subject) {
        UserDetails user = principalUserDetailsService.loadUserByUsername(subject);
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    private String parseBearerToken(HttpServletRequest request) {
        // Bearer Token 파싱 로직
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(authHeader -> authHeader.substring(7))
                .orElse(null);
    }
}
