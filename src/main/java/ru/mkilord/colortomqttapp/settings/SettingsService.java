package ru.mkilord.colortomqttapp.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.SettingConfig;
import ru.mkilord.colortomqttapp.params.Param;

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

    SettingConfig config;

    @PostConstruct
    public void init() {
        this.settingsFilePath = config.getSettingsFilePath();
        loadOrRestoreToDefaultSettings();
    }

    public void save() {
        var objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(settingsFilePath), settings);
            log.debug("Saved settings to {}", settingsFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save settings to file " + settingsFilePath, e);
        }
    }

    public void restoreToDefaultSettingsFromConfig() {
        log.debug("Restoring to default settings!");
        var defaultSettingsMap = config.getDefaultSettings();

        ifDebugPrintSettingsToLog(defaultSettingsMap);

        settings = new Settings(defaultSettingsMap);
        save();
    }

    private void loadOrRestoreToDefaultSettings() {
        var objectMapper = new ObjectMapper();
        try {
            settings = objectMapper.readValue(new File(settingsFilePath), Settings.class);
            log.debug("Loaded settings from {}", settingsFilePath);
            ifDebugPrintSettingsToLog(settings.params());
        } catch (IOException e) {
            log.warn("Failed to load settings from file {}", settingsFilePath);
            log.warn("Apply default settings!");
            restoreToDefaultSettingsFromConfig();
        }
    }

    private void ifDebugPrintSettingsToLog(Map<String, Param> settingsMap) {
        if (log.isDebugEnabled()) {
            var stringJoiner = new StringJoiner("; ");
            settingsMap.forEach((key, value) -> stringJoiner.add(key + "=" + value));
            log.debug(stringJoiner.toString());
        }
    }
}

