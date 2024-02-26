package com.example.shipgofunding.UserDetatilService;

import com.example.shipgofunding.domain.User;
import com.example.shipgofunding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserDetailservice implements UserDetailsService {

    private final UserRepository userRepository;

    //로그인 인증
    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(email));
    }

}