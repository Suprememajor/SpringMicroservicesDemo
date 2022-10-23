package com.supremecorp.serviceregistrationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableEurekaServer
@PropertySource("classpath:registration-server.properties")
public class ServiceRegistrationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistrationServerApplication.class, args);
    }
}
