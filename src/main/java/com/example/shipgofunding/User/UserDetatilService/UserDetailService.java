package com.example.shipgofunding.User.UserDetatilService;


import com.example.shipgofunding.User.domain.User;
import com.example.shipgofunding.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    //로그인 인증
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user!=null){
            return new UserDetailsService(user);
        }
}

