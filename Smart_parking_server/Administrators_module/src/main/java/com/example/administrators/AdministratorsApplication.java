package com.example.administrators;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableFeignClients(basePackages = "com.feign.api")
////@EnableDiscoveryClient  //@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。
//@EnableCircuitBreaker
////Hystrix开启
//@EnableHystrix
public class AdministratorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministratorsApplication.class, args);
    }


}
