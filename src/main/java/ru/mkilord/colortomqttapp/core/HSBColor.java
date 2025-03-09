package ru.mkilord.colortomqttapp.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.awt.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
public class HSBColor {
    float hue;
    float saturation;
    float brightness;

    public HSBColor(Color color) {
        var hsbVals = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.hue = hsbVals[0] * 360;
        this.saturation = hsbVals[1] * 100;
        this.brightness = hsbVals[2] * 100;
    }
}
