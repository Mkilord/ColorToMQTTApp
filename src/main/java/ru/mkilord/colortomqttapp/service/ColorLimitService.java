package ru.mkilord.colortomqttapp.service;

import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.BindSettings;
import ru.mkilord.colortomqttapp.core.HSBColor;

import java.util.Properties;

@Service
public class ColorLimitService implements BindSettings {
    int minHue, maxHue;
    int minSaturation, maxSaturation;
    int minBrightness, maxBrightness;

    @Override
    public void applySettings(Properties props) {
        this.maxHue = Integer.parseInt(props.getProperty("maxHue"));
        this.minHue = Integer.parseInt(props.getProperty("minHue"));
        this.maxSaturation = Integer.parseInt(props.getProperty("maxSaturation"));
        this.minSaturation = Integer.parseInt(props.getProperty("minSaturation"));
        this.maxBrightness = Integer.parseInt(props.getProperty("maxBrightness"));
        this.minBrightness = Integer.parseInt(props.getProperty("minBrightness"));
    }

    private float limit(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    public HSBColor limit(HSBColor color) {
        color.setHue(limit(color.getHue(), minHue, minHue));
        color.setSaturation(limit(color.getSaturation(), minSaturation, maxSaturation));
        color.setBrightness(limit(color.getBrightness(), minBrightness, maxBrightness));
        return color;
    }
}
