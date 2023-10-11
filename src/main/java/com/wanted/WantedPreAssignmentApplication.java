package com.wanted;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WantedPreAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(WantedPreAssignmentApplication.class, args);
    }

}
