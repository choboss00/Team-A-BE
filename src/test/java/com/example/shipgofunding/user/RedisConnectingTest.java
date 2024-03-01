package com.example.shipgofunding.user;

import com.example.shipgofunding.user.controller.VerficationController;
import com.example.shipgofunding.user.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedisConnectingTest {

        @Autowired


        private VerficationController verficationController;

        @Test
        public void testSendMail_Success() throws Exception {
            // Given
            UserRequest.SignupRequestDTO requestDTO = new UserRequest.SignupRequestDTO();
            requestDTO.setEmail("alzl7410@gmail.com");
            verficationController.sendmail(requestDTO);


    }
}



