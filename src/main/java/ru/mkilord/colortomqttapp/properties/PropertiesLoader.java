package ru.mkilord.colortomqttapp.properties;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.PropertiesConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PropertiesLoader {

    PropertiesConfig config;

    Properties properties = new Properties();

    public void loadPropertiesFromFile() throws IOException {
        var propertiesFile = config.getSettingsFilePath();
        var fis = new FileInputStream(propertiesFile);
        properties.load(fis);
        fis.close();
        log.debug("Loaded properties from {}", propertiesFile);
    }

    @PostConstruct
    public void loadPropertiesFromFileOrElseLoadDefault() {
        log.debug("Loading properties from file {}", config.getSettingsFilePath());
        try {
            loadPropertiesFromFile();
        } catch (IOException e) {
            log.debug("Could not load properties from file {}", config.getSettingsFilePath());
            loadDefaultProperties();
        }
    }

    public void loadDefaultProperties() {
        properties.clear();
        properties.putAll(config.getDefaultSettings());
        log.debug("Loaded default properties!");
    }

    public Properties getPropertiesClone() {
        properties.clone();
        log.debug("Cloned properties from {}", properties);
        return properties;
    }

    public String getProperty(String key) {
        log.debug("Getting property {}", key);
        return properties.getProperty(key);
    }
}
