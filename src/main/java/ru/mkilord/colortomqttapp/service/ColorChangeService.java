package ru.mkilord.colortomqttapp.service;

import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.BindSettings;
import ru.mkilord.colortomqttapp.core.detector.ColorChangeMatcher;

import java.awt.*;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE)
public final class ColorChangeService implements BindSettings {
    Color oldColor = Color.BLACK;
    int sensitivity;

    @Override
    public void applySettings(Properties props) throws RuntimeException {
        this.sensitivity = Integer.parseInt(props.getProperty("sensitivity"));
    }

    public boolean hasColorChanged(Color newColor) {
        if (ColorChangeMatcher.hasColorChanged(oldColor, newColor, sensitivity)) {
            oldColor = newColor;
            return true;
        } else return false;
    }
}
