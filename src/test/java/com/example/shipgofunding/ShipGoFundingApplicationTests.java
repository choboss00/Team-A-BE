package com.example.shipgofunding;

import com.example.shipgofunding.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ShipGoFundingApplicationTests {


    @Test
    void contextLoads() {
        System.out.println("테스트가 로드되었습니다.");
    }
}


