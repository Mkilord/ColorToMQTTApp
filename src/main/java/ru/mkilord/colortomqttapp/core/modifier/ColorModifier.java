package ru.mkilord.colortomqttapp.core.modifier;

import ru.mkilord.colortomqttapp.core.HSBColor;

import java.awt.*;

public interface ColorModifier {
    HSBColor modify(HSBColor color);
}
