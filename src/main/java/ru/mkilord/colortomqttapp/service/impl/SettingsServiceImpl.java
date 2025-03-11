package ru.mkilord.colortomqttapp.service.impl;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.mkilord.colortomqttapp.config.SettingsConfig;
import ru.mkilord.colortomqttapp.service.SettingsService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class SettingsServiceImpl implements SettingsService {

    SettingsConfig config;

    @Override
    public void save(Properties editedProperties) {
        var propertiesFile = config.getSettingsFilePath();

        try (var fos = new OutputStreamWriter(new FileOutputStream(propertiesFile.toFile()), StandardCharsets.UTF_8)) {
            editedProperties.store(fos, "Application settings");
            log.debug("Settings saved to {}", propertiesFile);
        } catch (IOException e) {
            log.error("Failed to save settings: {}", propertiesFile, e);
        }
    }

    @Override
    public Properties load() throws IOException {
        var propertiesFile = config.getSettingsFilePath();

        try (var fis = new FileInputStream(propertiesFile.toFile())) {
            var properties = new Properties();
            properties.load(fis);
            log.debug("Loaded properties from {}", propertiesFile);
            return properties;
        }
    }

    @Override
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

    @Override
    public Properties loadDefault() {
        var properties = new Properties();
        properties.putAll(config.getDefaultSettings());
        log.debug("Loaded default properties!");
        return properties;
    }
}
