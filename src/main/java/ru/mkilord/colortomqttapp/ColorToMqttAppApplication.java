package ru.mkilord.colortomqttapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class ColorToMqttAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColorToMqttAppApplication.class, args);
    }

}
