package ru.amironnikov.dictionaries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DictionariesApplication {
    public static void main(String[] args) {
        SpringApplication.run(DictionariesApplication.class, args);
    }
}
