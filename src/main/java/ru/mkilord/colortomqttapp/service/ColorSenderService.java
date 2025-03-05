package ru.mkilord.colortomqttapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.BindSettings;

import java.awt.*;
import java.util.Properties;

@Service
@Log4j2
@AllArgsConstructor
public final class ColorSenderService implements BindSettings {
    MQTTClientService mqttClientService;

    public void send(Color color) {
        mqttClientService.sendColor(color);
        log.info("Sending color: " + color);
    }

    @Override
    public void applySettings(Properties props) {
        mqttClientService.applySettings(props);
    }

}
