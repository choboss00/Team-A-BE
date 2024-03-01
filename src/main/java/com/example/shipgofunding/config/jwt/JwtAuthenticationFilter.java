package com.example.shipgofunding.config.jwt;

import com.example.shipgofunding.config.auth.PrincipalUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        String token = parseBearerToken(request);
        if (token != null && !token.isBlank()) {
            try {
                String subject = tokenProvider.validateTokenAndGetSubject(token); // 토큰에서 subject 추출 및 만료 시간 검증
                UserDetails user = principalUserDetailsService.loadUserByUsername(subject);

                // 사용자 존재 여부 확인 후 인증 객체 생성 및 SecurityContext에 저장
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (SignatureException e) {
                request.setAttribute("exception", "유효하지 않은 JWT 토큰 서명입니다.");
            } catch (MalformedJwtException e) {
                request.setAttribute("exception", "손상된 JWT 토큰입니다.");
            } catch (ExpiredJwtException e) {
                request.setAttribute("exception", "만료된 JWT 토큰입니다.");
            } catch (UnsupportedJwtException e) {
                request.setAttribute("exception", "지원하지 않는 JWT 토큰입니다.");
            } catch (IllegalArgumentException e) {
                request.setAttribute("excepiton","JWT 토큰 내에 정보가 없습니다.");
            }
        }

        filterChain.doFilter(request, response);
    }
    private String parseBearerToken(HttpServletRequest request) {
        // Bearer Token 파싱 로직
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(authHeader -> authHeader.substring(7))
                .orElse(null);
    }

}
