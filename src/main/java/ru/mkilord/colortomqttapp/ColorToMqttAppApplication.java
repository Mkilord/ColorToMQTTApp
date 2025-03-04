package ru.mkilord.colortomqttapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.mkilord.colortomqttapp.service.SettingsService;

@SpringBootApplication
public class ColorToMqttAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ColorToMqttAppApplication.class, args);
    SettingsService settingsService = null;
    var properties = settingsService.loadOrElseLoadDefault();
    settingsService.save(properties);

    }

}
