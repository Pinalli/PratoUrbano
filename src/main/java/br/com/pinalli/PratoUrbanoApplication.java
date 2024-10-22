package br.com.pinalli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.pinalli.repository")

public class PratoUrbanoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PratoUrbanoApplication.class, args);
    }
}