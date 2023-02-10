package com.example.vehicle.config;

import io.minio.MinioClient;
import org.example.saltfish.service.MinioServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioServiceRule {
    @Value("http://127.0.0.1")
    private String server;

    @Value("9000")
    private int port;

    @Value("admin")
    private String accessKey;

    @Value("12345678")
    private String secretKey;

//    // 桶名
//    private String bucket;

//    // 统一前缀
//    private String urlPrefix;

    /**
     * 建造者模式
     * @return
     */
    @Bean
    public MinioClient minioClient(){
        return  MinioClient.builder()
                .endpoint(server,port,false)
                .credentials(accessKey,secretKey)
                .build();
    }

    @Bean
    public MinioServiceImpl doMinioService(){
        return new MinioServiceImpl();
    }
}
