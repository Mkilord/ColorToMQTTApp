package ru.mkilord.colortomqttapp.core.tracker;

import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class ToleranceColorStateTracker extends ColorStateTracker {

    public static final String HUE_TOLERANCE_KEY = "hueTolerance";
    public static final String SATURATION_TOLERANCE_KEY = "saturationTolerance";
    public static final String BRIGHTNESS_TOLERANCE_KEY = "brightnessTolerance";

    float hueTolerance;
    float saturationTolerance;
    float brightnessTolerance;

    public ToleranceColorStateTracker(Properties properties) {
        this.hueTolerance = Float.parseFloat(properties.getProperty(HUE_TOLERANCE_KEY));
        this.saturationTolerance = Float.parseFloat(properties.getProperty(SATURATION_TOLERANCE_KEY));
        this.brightnessTolerance = Float.parseFloat(properties.getProperty(BRIGHTNESS_TOLERANCE_KEY));
    }

    @Override
    public boolean hasColorChanged(Color color) {
        var hsv1 = new float[3];
        var hsv2 = new float[3];
        var curColor = getCurrentColor();

        Color.RGBtoHSB(curColor.getRed(), curColor.getGreen(), curColor.getBlue(), hsv1);
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv2);

        var dh = Math.abs(hsv1[0] - hsv2[0]);
        dh = Math.min(dh, 1.0f - dh);

        var ds = Math.abs(hsv1[1] - hsv2[1]);
        var dv = Math.abs(hsv1[2] - hsv2[2]);

        var colorChanged = (dh > hueTolerance) || (ds > saturationTolerance) || (dv > brightnessTolerance);

        if (colorChanged) {
            setCurrentColor(color);
        }

        return colorChanged;
    }
}
