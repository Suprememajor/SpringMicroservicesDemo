package com.supremecorp.serviceregistrationserver.microservices.accounts;

import com.supremecorp.serviceregistrationserver.microservices.registration.RegistrationServer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false)
public class AccountServer {
    private static final Logger log = LoggerFactory.getLogger(AccountServer.class);

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.hikari.connection-timeout}")
    private long connTimeout;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maxPoolSize;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    public static void main(String[] args) {
        // Default to registration server on localhost
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

        System.setProperty("spring.config.name", "accounts-server");
        SpringApplication.run(AccountServer.class, args);
    }

    @Bean
    public DataSource dataSource() {
        log.info("Database url {}", databaseUrl);
        String[] dbInfo = databaseUrl.split("@");
        String[] credentials = dbInfo[0].split("[/:]+");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://" + dbInfo[1]);
        hikariConfig.setUsername(credentials[1]);
        hikariConfig.setPassword(credentials[2]);
        hikariConfig.setConnectionTimeout(connTimeout);
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        hikariConfig.setDriverClassName(driverClassName);
        return new HikariDataSource(hikariConfig);
    }
}
