package com.example.shipgofunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShipGoFundingApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShipGoFundingApplication.class, args);
    }

}
