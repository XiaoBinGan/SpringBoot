package com.practice;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer // 启用Spring Boot Admin Server
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args); // 启动Spring Boot应用程序
    }
}