package com.example.shipgofunding.user.service;

import com.example.shipgofunding.config.errors.exception.Exception400;
import com.example.shipgofunding.config.jwt.TokenProvider;
import com.example.shipgofunding.user.domain.RoleEnum;
import com.example.shipgofunding.user.repository.UserRepository;
import com.example.shipgofunding.user.request.UserRequest.SignupRequestDTO;
import com.example.shipgofunding.user.request.UserRequest.LoginRequestDTO;
import com.example.shipgofunding.user.response.UserResponse.LoginResponseWithTokenDTO;
import com.example.shipgofunding.user.response.UserResponse.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.shipgofunding.user.domain.User;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponseWithTokenDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception400(null, "아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception400(null, "아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        String token = tokenProvider.createToken(user.getEmail());

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(user.getEmail(), user.getNickname(), user.getImage(), user.getId());

        return new LoginResponseWithTokenDTO(loginResponseDTO, token);
    }

    @Transactional
    public void signup(SignupRequestDTO requestDTO) {
        // 비밀번호 암호화
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        // TO-DO : 유저의 역할을 구분하는 로직 작성

        // 저장
        userRepository.save(requestDTO.toEntity(RoleEnum.USER));
    }
}
