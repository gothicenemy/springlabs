package com.example.firstlab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order; // Не забудь цей імпорт

@SpringBootApplication
@Order(2) // Це означає, що цей Run виконається другим
public class FirstlabApplication implements CommandLineRunner {

    public static void main(String[] args) {
        System.out.println("Begin of main");
        SpringApplication.run(FirstlabApplication.class, args);
        System.out.println("End of main");
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello from Spring Boot!");
    }
}