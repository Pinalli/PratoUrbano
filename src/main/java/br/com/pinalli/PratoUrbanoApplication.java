package br.com.pinalli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.pinalli.repository")
@EnableDiscoveryClient
public class PratoUrbanoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PratoUrbanoApplication.class, args);
    }
}