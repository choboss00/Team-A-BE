package com.example.shipgofunding.repository;

import com.example.shipgofunding.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
//이메일로 사용자 정보 가져오기
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}