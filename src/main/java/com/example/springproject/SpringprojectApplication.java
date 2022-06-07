package com.example.springproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class SpringprojectApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringprojectApplication.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

    }

}
