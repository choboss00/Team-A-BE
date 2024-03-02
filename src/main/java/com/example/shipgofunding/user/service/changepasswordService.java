package com.example.shipgofunding.user.service;

import com.example.shipgofunding.config.auth.PrincipalUserDetailsService;
import com.example.shipgofunding.config.errors.exception.Exception400;
import com.example.shipgofunding.user.domain.User;
import com.example.shipgofunding.user.repository.UserRepository;
import com.example.shipgofunding.user.request.UserRequest;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class changepasswordService {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PrincipalUserDetailsService principalUserDetailsService;



    public changepasswordService(PasswordEncoder passwordEncoder, UserRepository userRepository, PrincipalUserDetailsService principalUserDetailsService, User user) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.principalUserDetailsService = principalUserDetailsService;
    }
    @Transactional
    public void changePassword(UserRequest.PasswordDTO passwordDTO) {
            String password = passwordDTO.getPassword();
            String passwordcheck = passwordDTO.getPasswordcheck();
            String email = passwordDTO.getEmail();

            if (!password.equals(passwordcheck)) {
                throw new Exception400(null, "비밀번호를 동일하게 입력해주세요.");
            } else {

                User user = userRepository.findByEmail(email).orElse(null);
                if (user == null) {
                    throw new Exception400(null, "사용자가 존재하지 않습니다.");
                }

                if (passwordEncoder.matches(password, user.getPassword())) {
                    throw new Exception400(null, "기존 비밀번호 입니다.");
                }

                // 비밀번호 변경
                user.setPassword(passwordEncoder.encode(password));

                // 변경된 사용자 정보 저장 및 업데이트
                userRepository.save(user);
            }
        }
    }




