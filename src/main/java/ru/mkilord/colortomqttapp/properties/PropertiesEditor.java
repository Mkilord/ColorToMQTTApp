package ru.mkilord.colortomqttapp.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.mkilord.colortomqttapp.config.PropertiesConfig;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PropertiesEditor {
    PropertiesConfig config;

    PropertiesLoader propertiesLoader;
    @Getter
    Properties editedProperties;

    public void setProperty(String key, String value) {
        log.debug("Setting property {}", key);
        editedProperties.setProperty(key, value);
    }

    public String getProperty(String key) {
        log.debug("Getting property {}", key);
        return editedProperties.getProperty(key);
    }

    public void restoreDefaultProperties() {
        propertiesLoader.loadDefaultProperties();
        savePropertiesToFile();
        log.debug("Restoring default properties!");
    }

    public void savePropertiesToFile() {
        var propertiesFile = config.getSettingsFilePath();
        try (var fos = new OutputStreamWriter(new FileOutputStream(propertiesFile), StandardCharsets.UTF_8)) {
            editedProperties.store(fos, "Application settings");
            log.debug("Settings saved to {}", propertiesFile);
        } catch (IOException e) {
            log.error("Failed to save settings: {}", e.getMessage());
        }
    }
}
