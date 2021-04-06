package com.nscooper.hsbc.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.nscooper.hsbc.library"})
@Configuration
@EnableJpaRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}