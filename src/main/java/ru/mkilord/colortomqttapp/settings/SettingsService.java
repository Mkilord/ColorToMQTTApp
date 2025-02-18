package ru.mkilord.colortomqttapp.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.SettingsConfig;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SettingsService {

    @Getter
    @NonFinal
    Settings settings;
    @NonFinal
    String settingsFilePath;

    SettingsConfig settingsConfig;

    @PostConstruct
    public void init() {
        this.settingsFilePath = settingsConfig.getSettingsFilePath();
        settings = loadSettings(settingsConfig.getSettings());
    }

    private Settings loadSettings(Map<String, String> defaultSettings) {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(settingsFilePath), Settings.class);
        } catch (IOException e) {
            log.warn("Failed to load settings from file {}", settingsFilePath);
            log.warn("Apply default settings!");

            printDefaultSettingsToLogIfDebugEnabled(defaultSettings);
            return new Settings(defaultSettings);
        }
    }

    private void printDefaultSettingsToLogIfDebugEnabled(Map<String, String> defaultSettings) {
        if (log.isDebugEnabled()) {
            var stringJoiner = new StringJoiner("; ");
            defaultSettings.forEach((key, value) -> stringJoiner.add(key + "=" + value));
            log.debug(stringJoiner.toString());
        }
    }

    public void save() {
        var objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(settingsFilePath), settings);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save settings to file " + settingsFilePath, e);
        }
    }

    public String getSetting(String key) {
        return settings.getSetting(key);
    }
}

