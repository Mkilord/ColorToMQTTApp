package ru.mkilord.colortomqttapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ColorToMqttAppApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ColorToMqttAppApplication.class)
                .headless(false)
                .run(args);
    }

}
