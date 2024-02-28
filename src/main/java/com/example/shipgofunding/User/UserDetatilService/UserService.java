package com.example.shipgofunding.UserDetatilService;

import com.example.shipgofunding.domain.User;
import com.example.shipgofunding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.shipgofunding.UserDetatilService.SignupRequest;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;




    // 패스워드 암호화하여 유저 정보 저장
    public User save(SignupRequest dto) {
        User user = User.builder()
                .role(dto.getRole())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build();

        User savedUser = userRepository.save(user);
        return savedUser;
    }

}