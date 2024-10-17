package br.com.pinalli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.pinalli.repository")
public class PratoUrbano {

    public static void main(String[] args) {
        SpringApplication.run(PratoUrbano.class, args);
    }
}