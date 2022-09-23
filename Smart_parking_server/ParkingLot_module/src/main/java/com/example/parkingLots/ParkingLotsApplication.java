package com.example.parkingLots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableFeignClients(basePackages = "com.feign.api")
//@EnableCircuitBreaker
////Hystrix开启
//@EnableHystrix
public class ParkingLotsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingLotsApplication.class, args);
    }

}
