package ru.mkilord.colortomqttapp.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.BindSettings;

import java.awt.*;
import java.util.Properties;

@Service
@Log4j2
public final class ColorSenderService implements BindSettings {
    public void send(Color color) {
        log.info("Sending color: " + color);
    }

    @Override
    public void applySettings(Properties props) {
        log.info("Applying settings");
    }

}
