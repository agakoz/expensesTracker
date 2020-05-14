package com.ak.webApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ExpenseTracker extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(ExpenseTracker.class, args);
    }



}
