package com.example.shipgofunding.UserDetatilService;

import com.example.shipgofunding.domain.User;
import com.example.shipgofunding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public class UserDetailservice {
    @RequiredArgsConstructor
    @Service
    public class UserDetailService implements UserDetailsService {
        private final UserRepository userRepository;

        @Override
        public User loadUserByUsername(String email) {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException(email));
        }
    }
}
