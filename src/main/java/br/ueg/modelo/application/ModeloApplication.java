package br.ueg.modelo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


@SpringBootApplication
@EntityScan(basePackageClasses = { Jsr310JpaConverters.class }, basePackages = "br.ueg.modelo.*")
public class ModeloApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModeloApplication.class, args);
    }

}
