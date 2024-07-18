package cn.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: adminApplication
 * @author: Met.
 * @date: 2024/3/23
 **/
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class adminApplication {
    public static void main(String[] args) {
        SpringApplication.run(adminApplication.class, args);
    }
}
