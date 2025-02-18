package ru.mkilord.colortomqttapp.config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
@FieldDefaults(level = PRIVATE)
public class SettingsConfig {
    Map<String, String> settings;
    String settingsFilePath = Path.of(".").toAbsolutePath().normalize().toString();
}
