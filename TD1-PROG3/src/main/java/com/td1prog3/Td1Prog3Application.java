package com.td1prog3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.td1prog3", "model", "db"})
public class Td1Prog3Application {

    public static void main(String[] args) {
        SpringApplication.run(Td1Prog3Application.class, args);
    }

}
