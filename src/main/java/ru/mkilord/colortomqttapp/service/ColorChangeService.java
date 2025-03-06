package ru.mkilord.colortomqttapp.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.BindSettings;
import ru.mkilord.colortomqttapp.core.HSBColor;
import ru.mkilord.colortomqttapp.core.detector.ColorChangeMatcher;

import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE)
@RequiredArgsConstructor(access = PRIVATE)
public final class ColorChangeService implements BindSettings {
    @Getter
    HSBColor currentColor = new HSBColor(0, 0, 0);
    int sensitivity;
    final ColorChangeMatcher colorChangeMatcher = new ColorChangeMatcher();

    @Override
    public void applySettings(Properties props) throws RuntimeException {
        this.sensitivity = Integer.parseInt(props.getProperty("sensitivity"));
    }

    public boolean hasColorChanged(HSBColor newColor) {
        if (colorChangeMatcher.hasColorChanged(currentColor, newColor, sensitivity)) {
            currentColor = newColor;
            return true;
        } else return false;
    }
}
