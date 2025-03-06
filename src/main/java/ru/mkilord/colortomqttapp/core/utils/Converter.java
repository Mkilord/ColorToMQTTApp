package ru.mkilord.colortomqttapp.core.utils;

import lombok.extern.log4j.Log4j2;
import ru.mkilord.colortomqttapp.core.HSBColor;

import java.awt.*;
@Log4j2
public class Converter {
    public HSBColor rgbToHSV(int... rgb) {
        var hsbColor = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
        return new HSBColor(hsbColor[0], hsbColor[1], hsbColor[2]);
    }
}
