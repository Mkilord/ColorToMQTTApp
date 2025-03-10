package ru.mkilord.colortomqttapp.core.limit;

import lombok.experimental.FieldDefaults;
import ru.mkilord.colortomqttapp.core.HSBColor;

import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class DefaultColorLimit implements ColorLimit {

    float minHue, maxHue;
    float minSaturation, maxSaturation;
    float minBrightness, maxBrightness;

    public DefaultColorLimit(Properties properties) {
        this.maxHue = Float.parseFloat(properties.getProperty("maxHUE"));
        this.minHue = Float.parseFloat(properties.getProperty("minHUE"));
        this.maxSaturation = Float.parseFloat(properties.getProperty("maxSaturation"));
        this.minSaturation = Float.parseFloat(properties.getProperty("minSaturation"));
        this.maxBrightness = Float.parseFloat(properties.getProperty("maxBrightness"));
        this.minBrightness = Float.parseFloat(properties.getProperty("minBrightness"));
    }

    private float limit(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    @Override
    public HSBColor applyFor(HSBColor color) {
        var hue = limit(color.getHue(), minHue, maxHue);
        var sat = limit(color.getSaturation(), minSaturation, maxSaturation);
        var bright = limit(color.getBrightness(), minBrightness, maxBrightness);
        return new HSBColor(hue, sat, bright);
    }
}
