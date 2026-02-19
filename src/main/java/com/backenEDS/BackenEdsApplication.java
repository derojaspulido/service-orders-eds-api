package com.backenEDS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackenEdsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackenEdsApplication.class, args);
    }
}
