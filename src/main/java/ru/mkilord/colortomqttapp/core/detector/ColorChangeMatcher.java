package ru.mkilord.colortomqttapp.core.detector;

import ru.mkilord.colortomqttapp.core.HSBColor;

import java.util.Objects;

public class ColorChangeMatcher {
    public boolean hasColorChanged(HSBColor curHSV, HSBColor newHSV, int sensitivity) {
        if (Objects.isNull(curHSV) || Objects.isNull(newHSV)) {
            return true;
        }
        if (sensitivity < 0 || sensitivity > 100) {
            throw new IllegalArgumentException("Sensitivity must be from 0 to 100");
        }

        var deltaHue = Math.abs(curHSV.getHue() - newHSV.getHue());
        var deltaSaturation = Math.abs(curHSV.getSaturation() - newHSV.getSaturation());
        var deltaBrightness = Math.abs(curHSV.getBrightness() - newHSV.getBrightness());

        var colorDifference = (deltaHue * 0.6f) + (deltaSaturation * 0.2f) + (deltaBrightness * 0.2f);

        var threshold = 100 - sensitivity;

        return colorDifference > threshold;
    }
}

