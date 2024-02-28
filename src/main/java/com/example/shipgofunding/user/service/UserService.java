package com.example.shipgofunding.user.service;

import com.example.shipgofunding.config.errors.exception.Exception400;
import com.example.shipgofunding.config.jwt.TokenProvider;
import com.example.shipgofunding.user.repository.UserRepository;
import com.example.shipgofunding.user.request.UserRequest.LoginRequestDTO;
import com.example.shipgofunding.user.response.UserResponse.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.shipgofunding.user.domain.User;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .filter(u -> u.getPassword().equals(request.getPassword()))
                .orElseThrow(() -> new Exception400(null, "아이디 또는 비밀번호가 일치하지 않습니다."));

        String token = tokenProvider.createToken(String.format("%s %s", user.getId(), user.getRole()));

        return new LoginResponseDTO(user.getNickname(), user.getNickname(), token);
    }
}
