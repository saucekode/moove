package com.moove.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackageClasses = EmailApplication.class)
public class EmailApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }
}
