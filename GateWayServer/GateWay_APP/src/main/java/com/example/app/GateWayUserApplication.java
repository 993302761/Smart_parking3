package com.example.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GateWayUserApplication {
    //    http://localhost:8502/Administrators/getAllParking
    public static void main(String[] args) {
        SpringApplication.run(GateWayUserApplication.class,args);
    }
}
