package ru.mkilord.colortomqttapp.service;

import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.BindSettings;
import ru.mkilord.colortomqttapp.core.HSBColor;

import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE)
public class ColorLimitService implements BindSettings {
    float minHue, maxHue;
    float minSaturation, maxSaturation;
    float minBrightness, maxBrightness;

    @Override
    public void applySettings(Properties props) {
        this.maxHue = Float.parseFloat(props.getProperty("maxHUE"));
        this.minHue = Float.parseFloat(props.getProperty("minHUE"));
        this.maxSaturation = Float.parseFloat(props.getProperty("maxSaturation"));
        this.minSaturation = Float.parseFloat(props.getProperty("minSaturation"));
        this.maxBrightness = Float.parseFloat(props.getProperty("maxBrightness"));
        this.minBrightness = Float.parseFloat(props.getProperty("minBrightness"));
    }

    private float limit(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    public HSBColor applyLimit(HSBColor color) {
        var hue = limit(color.getHue(), minHue, maxHue);
        var sat = limit(color.getSaturation(), minSaturation, maxSaturation);
        var bright = limit(color.getBrightness(), minBrightness, maxBrightness);
        return new HSBColor(hue, sat, bright);
    }
}
