package com.example.inspect;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableEurekaClient
public class InspectProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(InspectProviderApplication.class, args);
    }


}
