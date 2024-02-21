package com.poly.ecommercestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.poly.ecommercestore.repository")
@EntityScan(basePackages = "com.poly.ecommercestore.entity")
public class ECommerceStoreApplication{

    public static void main(String[] args) {
        SpringApplication.run(ECommerceStoreApplication.class, args);
    }

}
