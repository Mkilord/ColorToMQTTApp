package ru.mkilord.colortomqttapp.core.detector;

import java.awt.*;

public class ColorChangeMatcher {
    /**
     * Проверяет, изменился ли цвет в зависимости от чувствительности.
     *
     * @param currentColor исходный цвет
     * @param newColor     новый цвет
     * @param sensitivity  чувствительность (0-100)
     * @return true, если цвет изменился, иначе false
     */

    public boolean hasColorChanged(Color currentColor, Color newColor, int sensitivity) {
        if (sensitivity < 0 || sensitivity > 100) {
            throw new IllegalArgumentException("Sensitivity must be from 0 to 100");
        }
        var curHSV = RGBtoHSB(currentColor);
        var newHSV = RGBtoHSB(newColor);

        var deltaHue = Math.abs(curHSV[0] - newHSV[0]) * 360;
        var deltaSaturation = Math.abs(curHSV[1] - newHSV[1]) * 100;
        var deltaBrightness = Math.abs(curHSV[2] - newHSV[2]) * 100;

        var colorDifference = (deltaHue * 0.6f) + (deltaSaturation * 0.2f) + (deltaBrightness * 0.2f);

        var threshold = 100 - sensitivity;

        return colorDifference > threshold;
    }

    private float[] RGBtoHSB(Color color) {
        return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
    }
}

