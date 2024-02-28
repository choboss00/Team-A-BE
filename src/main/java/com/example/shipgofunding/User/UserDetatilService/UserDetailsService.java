package com.example.shipgofunding.User.UserDetatilService;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private final com.example.shipgofunding.repository.UserRepository userRepository;

    //로그인 인증
    @Override
    public UserDetails loadUserByUsername(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(email));

    }



}

