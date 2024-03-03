package com.example.shipgofunding.user.repository;

import com.example.shipgofunding.user.domain.SnsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {

     //유저네임으로 사용자 정보 가져오기

        SnsUser findByUsername(String username);
}
