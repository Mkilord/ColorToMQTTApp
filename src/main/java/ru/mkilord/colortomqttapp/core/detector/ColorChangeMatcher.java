package ru.mkilord.colortomqttapp.core.detector;

import java.awt.Color;

public class ColorChangeMatcher {
    /**
     * Проверяет, изменился ли цвет в зависимости от чувствительности.
     *
     * @param oldColor     исходный цвет
     * @param newColor     новый цвет
     * @param sensitivity  чувствительность (0-100)
     * @return true, если цвет изменился, иначе false
     */

    public static boolean hasColorChanged(Color oldColor, Color newColor, int sensitivity) {
        if (sensitivity < 0 || sensitivity > 100) {
            throw new IllegalArgumentException("Чувствительность должна быть от 0 до 100");
        }
        var oldHSV = new float[3];
        var newHSV = new float[3];

        Color.RGBtoHSB(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(), oldHSV);
        Color.RGBtoHSB(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), newHSV);

        // Вычисляем разницу между оттенками (Hue), насыщенностью (Saturation) и яркостью (Brightness)
        var deltaHue = Math.abs(oldHSV[0] - newHSV[0]) * 360; // Переводим в градусы
        var deltaSaturation = Math.abs(oldHSV[1] - newHSV[1]) * 100;
        var deltaBrightness = Math.abs(oldHSV[2] - newHSV[2]) * 100;

        // Усредняем разницу (можно подстроить веса, если один параметр важнее)
        float colorDifference = (deltaHue * 0.6f) + (deltaSaturation * 0.2f) + (deltaBrightness * 0.2f);

        // Чувствительность 100 означает, что даже небольшое изменение будет замечено
        float threshold = 100 - sensitivity;

        return colorDifference > threshold;
    }
}

