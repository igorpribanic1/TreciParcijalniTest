package org.example.treciparcijalnitest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.example.treciparcijalnitest")

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}