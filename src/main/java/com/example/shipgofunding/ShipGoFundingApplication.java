package com.example.shipgofunding;

import com.example.shipgofunding.config.s3.S3UploadService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class ShipGoFundingApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml," + "classpath:aws.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(ShipGoFundingApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
