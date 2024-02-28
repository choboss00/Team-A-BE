package com.example.shipgofunding.user.repository;

import com.example.shipgofunding.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
//이메일로 사용자 정보 가져오기
public interface UserRepository extends JpaRepository<User, User> {
    Optional<User> findByEmail(String email);
}