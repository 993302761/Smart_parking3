package org.example.saltfish.config;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
//@Component
//@ConfigurationProperties(prefix = "minio")
@Getter
@Setter
public class Minio_Config {

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

}
