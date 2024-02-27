package com.example.shipgofunding.UserDetatilService;

import com.example.shipgofunding.domain.User;
import com.example.shipgofunding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    // 패스워드 암호화하여 유저 정보 저장
    public Long save(com.example.shipgofunding.UserDetatilService.SignupRequest dto) {
        return userRepository.save(User.builder()
                .role(dto.getRole())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                // 패스워드 암호화
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }

}