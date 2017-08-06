package com.cct.carcosttracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CarCostTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarCostTrackerApplication.class, args);
    }

    @GetMapping(value = "/")
    public String helloWorld() {
        return "Hello world!";
    }
}
