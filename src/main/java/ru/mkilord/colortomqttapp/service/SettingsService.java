package ru.mkilord.colortomqttapp.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

@Service
public interface SettingsService {

    void save(Properties editedProperties);

    Properties load() throws IOException;
    Properties loadOrElseLoadDefault();
    Properties loadDefault();
}
