package com.example.parkingLots;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo        //开启基于注解的dubbo功能
public class ParkingLotsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingLotsApplication.class, args);
    }

}
