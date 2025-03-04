package ru.mkilord.colortomqttapp.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.SettingsConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SettingsService {

    SettingsConfig config;

    public void save(Properties editedProperties) {
        var propertiesFile = config.getSettingsFilePath();

        try (var fos = new OutputStreamWriter(new FileOutputStream(propertiesFile.toFile()), StandardCharsets.UTF_8)) {
            editedProperties.store(fos, "Application settings");
            log.debug("Settings saved to {}", propertiesFile);
        } catch (IOException e) {
            log.error("Failed to save settings: {}", propertiesFile, e);
        }
    }

    public Properties load() throws IOException {
        var propertiesFile = config.getSettingsFilePath();

        try (var fis = new FileInputStream(propertiesFile.toFile())) {
            var properties = new Properties();
            properties.load(fis);
            log.debug("Loaded properties from {}", propertiesFile);
            return properties;
        }
    }

    public Properties loadOrElseLoadDefault() {
        var settingsFilePath = config.getSettingsFilePath();
        log.debug("Loading properties from file {}", settingsFilePath);
        try {
            return load();
        } catch (IOException e) {
            log.warn("Could not load properties from file {}", settingsFilePath);
            return loadDefault();
        }
    }

    public Properties loadDefault() {
        var properties = new Properties();
        properties.putAll(config.getDefaultSettings());
        log.debug("Loaded default properties!");
        return properties;
    }
}
